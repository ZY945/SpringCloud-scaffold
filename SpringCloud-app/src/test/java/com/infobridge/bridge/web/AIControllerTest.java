package com.infobridge.bridge.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaffold.commons.redis.dto.AttributeDTO;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private void post(String path, Object object) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(object)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    private void get(String path, Object object) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(object)))
//                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(nearbyUsers)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testGet() throws Exception {
        String author = "诸葛亮";
        get("/ai/test?auther=" + author, "");
    }

    @Test
    void testAttributePrompt() throws Exception {
        AttributeDTO build = AttributeDTO.builder()
                .attributes(Lists.list("高兴", "愤怒", "悲伤", "恐惧", "惊讶")).build();
        get("/ai/attributePrompt", build);
    }

}
