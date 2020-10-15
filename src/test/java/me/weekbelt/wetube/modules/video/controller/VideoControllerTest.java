package me.weekbelt.wetube.modules.video.controller;

import me.weekbelt.wetube.infra.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("파일 업로드 테스트")
    public void fileUploadTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file",
                "hello.txt",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "Hello, World!" .getBytes());

        String requestUrl = "/videos/upload";

        this.mockMvc.perform(multipart(requestUrl).file(file))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }
}