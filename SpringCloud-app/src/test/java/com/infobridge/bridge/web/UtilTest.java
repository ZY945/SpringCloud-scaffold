package com.infobridge.bridge.web;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UtilTest {

    /**
     * 测试读取
     */
    @Value("classpath:template/prompt.st")
    private Resource templateResource;

    @Test
    void testReadResource() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(templateResource.getInputStream(), StandardCharsets.UTF_8));
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
            System.out.println(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testListToStr() {
        List<String> attributes = Lists.list("开放性", "责任心", "外向性");
        StringBuilder stringBuilder = new StringBuilder();
        attributes.forEach(attribute -> stringBuilder.append(attribute).append(", "));
        String attributeStr = stringBuilder.substring(0, stringBuilder.length() - 2);
        System.out.println(attributeStr); //开放性, 责任心, 外向性
    }
}
