package com.infobridge.bridge.web.service;


import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.ResultCallback;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infobridge.bridge.config.OpenAIContent;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

@Service
@Slf4j
public class AIServiceImpl implements AIService {

    @Autowired
    private OpenAIContent openAIContent;

    @Autowired
    private ClientV4 client;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String requestIdTemplate = "mycompany-%d";

    @Override
    public String getAIChat(String serviceManufacturers, String templateContent) {
        if (serviceManufacturers.equals(openAIContent.getZhiPu())) {
            return zhiPu(templateContent);
        } else if (serviceManufacturers.equals(openAIContent.getTongYi())) {
            return tongYi(templateContent);
        }
        return zhiPu(templateContent);
    }

    //////////////////智普AI//////////////////
    private String zhiPu(String templateContent) {
        String res = null;
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), templateContent);
        messages.add(chatMessage);

        // com.zhipu.oapi.demo.V4OkHttpClientTest.requestIdTemplate 显示private
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.FALSE)
                .invokeMethod(Constants.invokeMethod)
                .messages(messages)
                .requestId(requestId)
                .build();
        ModelApiResponse invokeModelApiResp = client.invokeModelApi(chatCompletionRequest);
        try {
            res = objectMapper.writeValueAsString(invokeModelApiResp);

            log.info("model output:" + res);
            return res;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }


    //////////////////通义千问//////////////////
    private String tongYi(String templateContent) {
        try {
            return streamCallWithMessage(templateContent);
        } catch (NoApiKeyException | InputRequiredException e) {
            throw new RuntimeException(e);
        }
    }

    public static String streamCallWithMessage(String content)
            throws NoApiKeyException, ApiException, InputRequiredException {
        Generation gen = new Generation();
        Message userMsg =
                Message.builder().role(Role.USER.getValue()).content(content).build();
        GenerationParam param = GenerationParam.builder()
                .model("qwen-max")
                .messages(Collections.singletonList(userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE) // the result if message format.
                .topP(0.8).enableSearch(true) // set streaming output
                .incrementalOutput(true) // get streaming output incrementally
                .build();
        Flowable<GenerationResult> result = gen.streamCall(param);
        StringBuilder fullContent = new StringBuilder();
        result.blockingForEach(message -> {
            fullContent.append(message.getOutput().getChoices().get(0).getMessage().getContent());
//            log.info(JsonUtils.toJson(message));
        });
        log.info("Full content: \n" + fullContent.toString());
        return fullContent.toString();
    }


    @SuppressWarnings("unused")
    public static void streamCallWithCallback()
            throws NoApiKeyException, ApiException, InputRequiredException, InterruptedException {
        Generation gen = new Generation();
        Message userMsg =
                Message.builder().role(Role.USER.getValue()).content("如何做西红柿炖牛腩？").build();
        GenerationParam param = GenerationParam.builder()
                .model("${modelCode}")
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)  //set result format message
                .messages(Collections.singletonList(userMsg)) // set messages
                .topP(0.8)
                .incrementalOutput(true) // set streaming output incrementally
                .build();
        Semaphore semaphore = new Semaphore(0);
        StringBuilder fullContent = new StringBuilder();
        gen.streamCall(param, new ResultCallback<GenerationResult>() {

            @Override
            public void onEvent(GenerationResult message) {
                fullContent
                        .append(message.getOutput().getChoices().get(0).getMessage().getContent());
                System.out.println(message);
            }

            @Override
            public void onError(Exception err) {
                System.out.printf("Exception: %s%n", err.getMessage());
                semaphore.release();
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
                semaphore.release();
            }

        });
        semaphore.acquire();
        System.out.println("Full content: \n" + fullContent.toString());
    }
}
