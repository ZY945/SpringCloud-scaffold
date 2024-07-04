package com.infobridge.bridge.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;

/**
 * @summary fegin 客户端的自定义配置
 */
public class MyConfiguration {

    /**
     * 自定义重试机制
     *
     * @return
     */
    @Bean
    public Retryer feignRetryer() {
        //fegin提供的默认实现，最大请求次数为5，初始间隔时间为100ms，下次间隔时间1.5倍递增，重试间最大间隔时间为1s，
        return new Retryer.Default();
    }
}