package me.weekbelt.wetube.modules.video.controller;

import me.weekbelt.wetube.infra.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@MockMvcTest
class VideoApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("동영상 API 호출")
    void getVideos() throws Exception {
        // given
        String requestUri = "/api/videos";


    }
}