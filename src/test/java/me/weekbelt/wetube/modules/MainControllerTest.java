package me.weekbelt.wetube.modules;

import me.weekbelt.wetube.infra.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("메인 페이지")
    void mainPage() throws Exception {
        // given
        String requestUrl = "/";

        // then
        ResultActions resultActions = mockMvc.perform(get(requestUrl));

        // then
        resultActions
                .andExpect(model().attributeExists("videos"))
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(view().name("home"));
    }

    // TODO: 검색기능 구현시 수정
    @Test
    @DisplayName("검색 페이지")
    void searchVideo() throws Exception {
        // given
        String requestUrl = "/search";

        // when
        ResultActions resultActions = mockMvc.perform(get(requestUrl)
                .param("term", "spring"));

        // then
        resultActions
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(model().attributeExists("searchingBy"))
                .andExpect(model().attributeExists("videos"))
                .andExpect(view().name("videos/search"));
    }

    @Test
    @DisplayName("회원 가입 페이지")
    void joinPage() throws Exception {
        // given
        String requestUrl = "/join";

        // when
        ResultActions resultActions = mockMvc.perform(get(requestUrl));

        // then
        resultActions
                .andExpect(model().attributeExists("memberJoinForm"))
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(view().name("join"));
    }

    @Test
    @DisplayName("회원 가입 - 성공")
    void join_success() throws Exception {
        // given
        String requestUrl = "/join";

        // when
        ResultActions resultActions = mockMvc.perform(post(requestUrl)
                .param("name", "joohyuk")
                .param("email", "vfrvfr4207@hanmail.net")
                .param("password", "12345678")
                .param("password2", "12345678")
                .with(csrf()));

        // then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername("joohyuk"));
    }


    @Test
    @DisplayName("회원 가입 - 실패(입력값 오류)")
    void join_fail() throws Exception {
        // given
        String requestUrl = "/join";

        // when
        ResultActions resultActions = mockMvc.perform(post(requestUrl)
                .param("name", "")
                .param("email", "vfrvfr4207@hanmail.net")
                .param("password", "12345678")
                .param("password2", "12345678")
                .with(csrf()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(view().name("join"))
                .andExpect(model().hasErrors())
                .andExpect(unauthenticated());
    }
}