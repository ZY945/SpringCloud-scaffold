package com.infobridge.bridge.web.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infobridge.bridge.config.OpenAIContent;
import com.infobridge.bridge.web.service.AIService;
import com.scaffold.commons.redis.dto.AttributeDTO;
import com.scaffold.commons.utils.dto.ai.Response;
import com.scaffold.commons.utils.util.ResouceUtil;
import com.scaffold.commons.utils.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/ai")
public class AIController {
    @Autowired
    private OpenAIContent openAIContent;

    @Autowired
    private AIService aiService;

    @Value("classpath:template/prompt.st")
    private Resource templateResource;

    @Value("classpath:template/attribute.st")
    private Resource attributeTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 这个AI调用一般会限制并发量,可以设置先新手引导来进行减少用户体验的降低
     */
    @GetMapping("/attributePrompt")
    public Result<?> attributePrompt(@RequestBody AttributeDTO dto) {
        // 获取templateResource的文本
        String templateContent = ResouceUtil.readResouce(attributeTemplate);

        // 把list<String> 转化为 开放性, 责任心, 外向性
        List<String> attributes = dto.getAttributes();
        StringBuilder stringBuilder = new StringBuilder();
        attributes.forEach(attribute -> stringBuilder.append(attribute).append(", "));
        String attributeStr = stringBuilder.substring(0, stringBuilder.length() - 2);

        // 替换占位符
        templateContent = templateContent.replace("{attribute}", attributeStr);

        String res = aiService.getAIChat(openAIContent.getZhiPu(), templateContent);
        Response response = null;
        try {
            response = objectMapper.readValue(res, Response.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return Result.success(response.getResponseContent());
    }

    /**
     * 这个AI调用一般会限制并发量,可以设置先新手引导来进行减少用户体验的降低
     */
    @GetMapping("/zhiPu/test")
    public Result<?> zhiPuPromptTemplate(String author) {
        // 获取templateResource的文本
        String templateContent = ResouceUtil.readResouce(templateResource);
        // 替换占位符
        templateContent = templateContent.replace("{author}", author);

        String res = aiService.getAIChat(openAIContent.getZhiPu(), templateContent);
        Response response = null;
        try {
            response = objectMapper.readValue(res, Response.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return Result.success(response.getResponseContent());
    }

    /**
     * 这个AI调用一般会限制并发量,可以设置先新手引导来进行减少用户体验的降低
     */
    @GetMapping("/tongyi/test")
    public Result<?> tongYiPromptTemplate(String author) {
        // 获取templateResource的文本
        String templateContent = ResouceUtil.readResouce(templateResource);
        // 替换占位符
        templateContent = templateContent.replace("{author}", author);

        String res = aiService.getAIChat(openAIContent.getTongYi(), templateContent);
        return Result.success(res);
    }
}
