package com.scaffold.gateway.fegin;

import com.scaffold.commons.utils.vo.Result;
import com.scaffold.gateway.client.AuthWebClient;
import com.scaffold.gateway.client.FeignClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dongfeng
 * 2024-07-04 22:42
 */
@Service
public class UserReactiveClient implements AuthService {

    @Resource
    private FeignClient feignClient;

    @Resource
    private AuthWebClient authWebClient;

    @Value("${info-bridge-feign.user-service-url}")
    private String authUrl;

    private static Mono<Throwable> handleUnsuccessfulResponse(ClientResponse response) {
        return response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new RuntimeException("Request failed with status " + response.statusCode().value() + ". Response body: " + body)));
    }

    @Override
    public Mono<Result> authenticate(String token) {
        String url = authUrl + "/auth/user";


        Map<String, String> params = new HashMap<>();
//        params.put("limit", "10");

        HashMap<String, Object> body = new HashMap<>();
        body.put("token", token);
        return feignClient.restMonoPostJson(
                authWebClient.getWebClient(),
                url,
                body,
                Result.class,
                UserReactiveClient::handleUnsuccessfulResponse
        );
    }
}
