package com.infobridge.bridge.config;

import com.alibaba.dashscope.utils.Constants;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Data
public class OpenAIContent {

    private String zhiPu = "zhipu";

    private String tongYi = "tongyi";

    @Value("${AI.zhipu.apiKey}")
    private String ZHIPU_OPEN_API_KEY;

    @Value("${AI.tongyi.apiKey}")
    private String TONGYI_OPEN_API_KEY;

    @Bean
    public void init() {
        Constants.apiKey = TONGYI_OPEN_API_KEY;
    }
}
