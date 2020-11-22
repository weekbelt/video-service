package me.weekbelt.wetube.modules.member.controller;

import me.weekbelt.wetube.infra.MockMvcTest;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.member.MemberFactory;
import me.weekbelt.wetube.modules.member.form.MemberUpdateForm;
import me.weekbelt.wetube.modules.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
class MemberApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberFactory memberFactory;

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("프로파일 이미지 호출")
    void showProfileImage() throws Exception {
        // given
        Member member = memberFactory.createMember("tigers");
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "multipartImageProfile",
                "test.png",
                "image/png",
                "test image".getBytes());
        MemberUpdateForm memberUpdateForm = MemberUpdateForm.builder()
                .multipartImageProfile(mockMultipartFile)
                .build();
        memberService.updateProfile(member, memberUpdateForm);

        String requestUri = "/api/members/" + member.getName() + "/profileImage";

        // when
        ResultActions resultActions = mockMvc.perform(get(requestUri));

        // then
        resultActions
                .andExpect(status().isOk());

    }

}