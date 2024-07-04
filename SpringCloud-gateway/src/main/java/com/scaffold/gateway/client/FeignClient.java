package com.scaffold.gateway.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Function;

/**
 * https://blog.csdn.net/qq_33598419/article/details/126171796
 */
@Slf4j
@Component
public class FeignClient {


    // TODO 调用方法，响应Mono类型，自己可以按照需要创建实体来封装请求信息，这里仅作为例子
    public <T> Mono<T> restMono(WebClient smartWebClient,
                                HttpMethod httpMethod, String url,
                                Map<String, String> params, MultiValueMap<String, String> body,
                                MediaType reqMediaType, MediaType respMediaType,
                                Class<T> resultType, Function<ClientResponse, Mono<? extends Throwable>> unSuccessHandle) {
        return smartWebClient
                .method(httpMethod) // post get put ……
                .uri(url, params) // 这里的params是拼接在url中的，可以用@PathVariable接收的参数
                .accept(respMediaType) // contentType
                .contentType(reqMediaType) // contentType
                // 这里可以放入BodyInserters.from……多种方法，按需使用
                .body(BodyInserters.fromFormData(body)) // 构造的参数可以用@RequestParam、@RequestBody接收
                .retrieve() // 发送请求
                // 第一个参数判断状态，为调用方法的条件，第二个传入lambda表达式，返回Mono<异常>
                .onStatus(status -> !status.is2xxSuccessful(), unSuccessHandle)
                // 结果转换，将响应转换为什么类型的结果，结果为响应式类型Mono/Flux
                .bodyToMono(resultType);
    }

    // TODO 调用方法，响应Flux类型，自己可以按照需要创建实体来封装请求信息，这里仅作为例子
    public <T> Flux<T> restFlux(WebClient smartWebClient,
                                HttpMethod httpMethod, String url,
                                Map<String, String> params, MultiValueMap<String, String> body,
                                MediaType reqMediaType, MediaType respMediaType,
                                Class<T> resultType, Function<ClientResponse, Mono<? extends Throwable>> unSuccessHandle) {
        return smartWebClient
                .method(httpMethod) // post get put ……
                .uri(url, params) // 这里的params是拼接在url中的，可以用@PathVariable接收的参数
                .accept(respMediaType) // contentType
                .contentType(reqMediaType) // contentType
                // 这里可以放入BodyInserters.from……多种方法，按需使用
                .body(BodyInserters.fromFormData(body)) // 构造的参数可以用@RequestParam、@RequestBody接收
                .retrieve() // 发送请求
                // 第一个参数判断状态，为调用方法的条件，第二个传入lambda表达式，返回Mono<异常>
                .onStatus(status -> !status.is2xxSuccessful(), unSuccessHandle)
                // 结果转换，将响应转换为什么类型的结果，结果为响应式类型Mono/Flux
                .bodyToFlux(resultType);
    }

}
