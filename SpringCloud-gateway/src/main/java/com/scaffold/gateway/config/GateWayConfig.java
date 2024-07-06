package com.scaffold.gateway.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.scaffold.commons.utils.vo.Result;
import com.scaffold.gateway.fegin.AuthService;
import com.scaffold.gateway.util.TraceIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dongfeng
 * 2024-07-04 21:51
 */
@Slf4j
@Configuration
public class GateWayConfig implements GlobalFilter, Ordered {
    /**
     * 排除过滤的 uri 地址
     */
    private static final String LOGIN_URI = "/login";
    private static final String HEALTH_URI = "/health";

    @Resource
    private AuthService authService;

    //判断是否需要过滤
    public boolean notFilter(ServerHttpRequest request) {
        //是否执行该过滤器
        // log.info("===当前请求路径为:" + request.getURI().toString());
        //注册和登录接口不拦截，其他接口都要拦截校验 token
        return request.getURI().getPath().contains(LOGIN_URI)
                || request.getURI().getPath().contains(HEALTH_URI);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 获取请求响应对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = response.getHeaders();
        List<String> authorization = request.getHeaders().get("Authorization");


        // 检查请求方法是否为GET,只允许POST请求通行
        if (request.getMethod() == HttpMethod.GET) {
            // 在这里可以添加你的逻辑，例如记录日志、修改请求头等
            log.warn("===不允许GET请求,当前请求路径为:" + request.getURI() + "===");
            response.setStatusCode(HttpStatus.OK);
            //校验异常的返参格式处理，写入返参body
            String body = "{\"status\":-1,\"statusText\":\"不允许GET请求\"}";
            Flux<byte[]> flux = Flux.just(body.getBytes());
            Flux<DataBuffer> map = flux.map(bx -> response.bufferFactory().wrap(bx));
            headers.setContentType(MediaType.APPLICATION_JSON);
            return response.writeWith(map);
        }

        // 放行指定的请求
        if (notFilter(request)) {
            return chain.filter(exchange);
        }


        // 校验token
        String token = null;
        if (null != authorization) {
            token = authorization.stream().findFirst().orElse(null);
        }
        if (token == null || token.isEmpty()) {
            response.setStatusCode(HttpStatus.OK);
            //校验异常的返参格式处理，写入返参body
            String body = "{\"status\":-1,\"statusText\":\"没有token\"}";
            Flux<byte[]> flux = Flux.just(body.getBytes());
            Flux<DataBuffer> map = flux.map(bx -> response.bufferFactory().wrap(bx));
            headers.setContentType(MediaType.APPLICATION_JSON);
            return response.writeWith(map);

        }
        //2. 进行 权限认证的 逻辑
        String finalToken = token;
        return this.authenticate(token).flatMap(result -> {
            JSONObject dataJson = JSON.parseObject(JSON.toJSONString(result.getData()));
            if (result.getCode() == 200 && dataJson.getList("roles", String.class).contains("admin")) {
                // 通过校验
                // 把链路ID存放在头信息转发下去，并且放行
                String traceId = TraceIdUtil.buildTraceId();
                TraceIdUtil.setTraceId(traceId);
                ServerHttpRequest newRequest = request.mutate()
                        .header("Authorization", finalToken)
                        .header("Trace-id", traceId).build();

                ServerWebExchange newExchange = exchange.mutate()
                        .request(newRequest)
                        .response(response).build();
                return chain.filter(newExchange);
            } else {
                //校验未通过
                //阻止请求。
                response.setStatusCode(HttpStatus.OK);
                String body = "{\"status\":-1,\"statusText\":\"用户验证超时，请重新登录\"}";
                Flux<byte[]> flux = Flux.just(body.getBytes());
                Flux<DataBuffer> map = flux.map(bx -> response.bufferFactory().wrap(bx));
                headers.setContentType(MediaType.APPLICATION_JSON);
                return response.writeWith(map);
            }
        });
    }

    //token校验逻辑

    /**
     * gateway与feign冲突,feign是同步的
     */
    private Mono<Result> authenticate(String token) {
        return authService.authenticate(token);
    }

    /**
     * 该过滤器优先级最高
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
