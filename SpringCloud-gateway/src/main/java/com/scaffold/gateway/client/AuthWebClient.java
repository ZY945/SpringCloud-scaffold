package com.scaffold.gateway.client;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author dongfeng
 * 2024-07-05 0:41
 */
@Component
public class AuthWebClient {

    @Getter
    private final WebClient webClient;


    @Value("${info-bridge-feign.user-service-url}")
    private String authUrl;

    // ReactorLoadBalancerExchangeFilterFunction是LoadBalance的一个负载
    // 可以适配nacos的服务发现，将其注入到WebClient即可实现服务名称调用及负载,否则引入spring-cloud-starter-loadbalancer通过http
    public AuthWebClient() {
        this.webClient = WebClient.builder()
//                .filter(lbFunction)
                .baseUrl(authUrl) // TODO 这里可以配置为公共的baseUrl，也可以在调用方法中传入
                // 默认请求头
//                .defaultHeader(WEB_CLIENT_HEADER, WEB_CLIENT_HEADER_VALUE)
                .build();
    }

}

