package com.infobridge.bridge.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaffold.commons.redis.dto.NearByDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NearByControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @MockBean
//    private RedisGeoServiceImpl redisGeoService;

    private final ObjectMapper objectMapper = new ObjectMapper();


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

    private NearByDTO initUsers() {
        double baseLongitude = 113.66;
        double baseLatitude = 34.75;

        Random random = new Random();
        double range = random.nextInt(100); // 这个范围可以根据你的需求调整
        String userId = generateRandomUserId();
        double randomLongitude = baseLongitude + (random.nextDouble() * 2 * range - range);
        double randomLatitude = baseLatitude + (random.nextDouble() * 2 * range - range);

        return NearByDTO.builder()
                .userId(userId)
                .longitude(String.valueOf(randomLongitude))
                .latitude(String.valueOf(randomLatitude))
                .build();

    }

    private static String generateRandomUserId() {
        return "User" + new Random().nextInt(10000);
    }

    @Test
    void shouldUpdateUserLocation() throws Exception {
        // 模拟用户
        for (int i = 0; i < 100; i++) {
            NearByDTO nearByDTO = initUsers();
            post("/nearby/location/update", nearByDTO);
        }
    }


    @Test
    void neighborhoodByPoint() throws Exception {
        NearByDTO dto = NearByDTO.builder()
                .radius("1000.0")
                .longitude("111.41")
                .latitude("39.90")
                .limitMember("3")
                .build();

        get("/nearby/location/neighborhoodByPoint", dto);
    }

    @Test
    void neighborhoodByMember() throws Exception {
        NearByDTO dto = NearByDTO.builder()
                .userId("beiJing")
                .radius("1000.0")
                .longitude("111.41")
                .latitude("39.90")
                .build();

        get("/nearby/location/neighborhoodByMember", dto);
    }

}