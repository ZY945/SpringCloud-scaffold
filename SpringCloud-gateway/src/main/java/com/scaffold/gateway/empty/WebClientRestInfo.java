package com.scaffold.gateway.empty;

import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Data
public class WebClientRestInfo<T> {

    /**
     * 请求方式
     */
    private HttpMethod httpMethod = HttpMethod.GET;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求path参数
     */
    private Map<String, Object> pathVariable;

    /**
     * 请求表单/Body
     */
    private MultiValueMap<String, Object> formValues;

    /**
     * 编码
     */
    private MediaType reqMediaType = MediaType.APPLICATION_JSON;
    private MediaType respMediaType = MediaType.APPLICATION_JSON;

    /**
     * 是否为Flux
     */
    private boolean isFlux = false;

    /**
     * 返回值类型
     */
    private Class<T> resultType;
}

