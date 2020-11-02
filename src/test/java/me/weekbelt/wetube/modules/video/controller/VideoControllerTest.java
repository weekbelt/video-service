package me.weekbelt.wetube.modules.video.controller;

import me.weekbelt.wetube.infra.MockMvcTest;
import me.weekbelt.wetube.modules.member.WithMember;
import me.weekbelt.wetube.modules.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
class VideoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;


    @Test
    @WithMember("joohyuk")
    @DisplayName("파일 업로드 화면")
    void uploadVideoPage() throws Exception {
        // given
        String requestUrl = "/videos/upload";

        // then
        ResultActions resultActions = mockMvc.perform(get(requestUrl));

        // when
        resultActions
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(model().attributeExists("videoUploadForm"))
                .andExpect(view().name("videos/upload"));
    }

    // TODO: 파일 업로드 테스트 구현
//    @Test
//    @WithMember("joohyuk")
//    void uploadVideo() throws Exception {
//        // given
//        Member member = memberRepository.findByName("joohyuk").orElse(null);
//
//        MockMultipartFile mockMultipartFile = new MockMultipartFile("video", "video.mp4", "video/mp4", "video".getBytes());
//        // when
//        ResultActions resultActions = mockMvc.perform(multipart("/videos/upload").file(mockMultipartFile));
//
//        // then
//        resultActions
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/videos"));
//
//    }
}