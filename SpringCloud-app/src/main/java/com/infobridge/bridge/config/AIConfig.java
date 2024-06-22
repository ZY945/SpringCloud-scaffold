package com.infobridge.bridge.config;

import com.zhipu.oapi.ClientV4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AIConfig {

    @Autowired
    private OpenAIContent openAIContent;

    @Bean(name = "clientV4")
    public ClientV4 clientV4() {
        return new ClientV4.Builder(openAIContent.getZHIPU_OPEN_API_KEY()).build();
    }
}
