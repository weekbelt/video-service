package me.weekbelt.wetube.modules.member.controller;

import me.weekbelt.wetube.infra.MockMvcTest;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.member.MemberFactory;
import me.weekbelt.wetube.modules.member.WithMember;
import me.weekbelt.wetube.modules.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberFactory memberFactory;

    @AfterEach
    void afterEach() {
        memberRepository.deleteAll();
    }

    @Test
    @WithMember("joohyuk")
    @DisplayName("회원 정보 뷰 - (수정권한 O)")
    void userDetail_isOwner() throws Exception {
        // given
        Member member = memberRepository.findByName("joohyuk").orElse(null);
        assert member != null;
        String requestUrl = "/members/profile/" + member.getName();

        // when
        ResultActions resultActions = mockMvc.perform(get(requestUrl));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("memberReadForm"))
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(model().attributeExists("member"))
                .andExpect(model().attribute("isOwner", true))
                .andExpect(view().name("users/userDetail"));
    }

    @Test
    @WithMember("joohyuk")
    @DisplayName("회원 정보 뷰 - (수정권한 X)")
    void userDetail_isNotOwner() throws Exception {
        // given
        Member twins = memberFactory.createMember("tigers");
        String requestUrl = "/members/profile/" + twins.getName();

        // when
        ResultActions resultActions = mockMvc.perform(get(requestUrl));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("memberReadForm"))
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(model().attribute("isOwner", false))
                .andExpect(view().name("users/userDetail"));
    }

    @Test
    @WithMember("joohyuk")
    @DisplayName("회원 업데이트 뷰")
    void editProfileView() throws Exception {
        // given
        String requestUrl = "/members/edit-profile";

        // when
        ResultActions resultActions = mockMvc.perform(get(requestUrl));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("memberUpdateForm"))
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(model().attributeExists("member"))
                .andExpect(view().name("users/editProfile"));
    }

    @Test
    @WithMember("joohyuk")
    @DisplayName("회원 업데이트 - 성공")
    void editProfile_success() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "multipartImageProfile", "twins.png", "image/png", "twins.png".getBytes());
        // given
        String requestUrl = "/members/edit-profile";

        // when
        ResultActions resultActions = mockMvc.perform(multipart(requestUrl)
                .file(file)
                .with(csrf()));

        // when
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("message"))
                .andExpect(redirectedUrl("/members/profile/joohyuk"));
    }

    @Test
    @WithMember("joohyuk")
    @DisplayName("회원 이름 변경 뷰")
    void changeNameView() throws Exception {
        // given
        String requestUrl = "/members/change-name";

        // when
        ResultActions resultActions = mockMvc.perform(get(requestUrl));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("member"))
                .andExpect(model().attributeExists("changeNameForm"))
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(view().name("users/changeName"));
    }

    @Test
    @WithMember("joohyuk")
    @DisplayName("회원 이름 변경 - 성공")
    void changeNameView_success() throws Exception {
        // given
        String requestUrl = "/members/change-name";

        // when
        ResultActions resultActions = mockMvc.perform(post(requestUrl)
                .param("name", "tigers")
                .with(csrf()));

        // when
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("message"))
                .andExpect(redirectedUrl("/members/profile/tigers"));
    }

    @ParameterizedTest(name = "{index} {displayName} name={0}")
    @WithMember("joohyuk")
    @DisplayName("회원 이름 변경 - 실패")
    @ValueSource(strings = {"joohyuk", "@#$%@#$", "", "asdfasdfasdfhawkerjfghaskdfh"})
    void changeNameView_fail(String name) throws Exception {
        // given
        String requestUrl = "/members/change-name";

        // when
        ResultActions resultActions = mockMvc.perform(post(requestUrl)
                .param("name", name)
                .with(csrf()));

        // when
        resultActions
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(model().attributeExists("member"))
                .andExpect(view().name("users/changeName"));
    }

    @Test
    @WithMember("joohyuk")
    @DisplayName("이메일 업데이트 뷰")
    void changeEmailView() throws Exception {
        // given
        String requestUrl = "/members/change-email";

        // when
        ResultActions resultActions = mockMvc.perform(get(requestUrl));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("changeEmailForm"))
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(view().name("users/changeEmail"));
    }

    @Test
    @WithMember("joohyuk")
    @DisplayName("이메일 업데이트 - 성공")
    void changeEmail_success() throws Exception {
        // given
        String requestUrl = "/members/change-email";

        // when
        ResultActions resultActions = mockMvc.perform(post(requestUrl)
                .param("email", "lg@twins.com")
                .with(csrf()));

        // when
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("message"))
                .andExpect(redirectedUrl("/members/profile/joohyuk"));

        Member member = memberRepository.findByEmail("lg@twins.com").orElse(null);
        assertThat(member).isNotNull();
    }

    @Test
    @WithMember("joohyuk")
    @DisplayName("이메일 업데이트 - 실패(이미 존재하는 이메일) ")
    void changeEmail_fail_existsEmail() throws Exception {
        // given
        String requestUrl = "/members/change-email";
        memberFactory.createMember("tigers");

        // when
        ResultActions resultActions = mockMvc.perform(post(requestUrl)
                .param("email", "tigers@email.com")
                .with(csrf()));

        // when
        resultActions
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(model().attributeExists("member"))
                .andExpect(view().name("users/changeEmail"));
    }

    @Test
    @WithMember("joohyuk")
    @DisplayName("패스워드 변경 뷰")
    void changePasswordView() throws Exception {
        // given
        String requestUrl = "/members/change-password";

        // when
        ResultActions resultActions = mockMvc.perform(get(requestUrl));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("changePasswordForm"))
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(view().name("users/changePassword"));
    }

    @Test
    @WithMember("joohyuk")
    @DisplayName("비밀번호 변경 - 성공")
    void changePassword_success() throws Exception {
        // given
        String requestUrl = "/members/change-password";

        // when
        ResultActions resultActions = mockMvc.perform(post(requestUrl)
                .param("newPassword", "987654321")        // 원래 비번 12345678
                .param("newPassword1", "987654321")
                .with(csrf()));

        // when
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("message"))
                .andExpect(redirectedUrl("/members/profile/joohyuk"));
    }

    @Test
    @WithMember("joohyuk")
    @DisplayName("비밀번호 변경 - 실패(패스워드 일치 X) ")
    void changePassword_fail_diffPassword() throws Exception {
        // given
        String requestUrl = "/members/change-password";

        // when
        ResultActions resultActions = mockMvc.perform(post(requestUrl)
                .param("newPassword", "987654123")    // 원래 패스워드 12345678
                .param("newPassword1", "1231231231")
                .with(csrf()));

        // when
        resultActions
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(model().attributeExists("member"))
                .andExpect(view().name("users/changePassword"));
    }
}