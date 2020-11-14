package me.weekbelt.wetube.modules.video.controller;

import me.weekbelt.wetube.infra.MockMvcTest;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.member.MemberFactory;
import me.weekbelt.wetube.modules.member.WithMember;
import me.weekbelt.wetube.modules.member.repository.MemberRepository;
import me.weekbelt.wetube.modules.video.Video;
import me.weekbelt.wetube.modules.video.VideoFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.InputStream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
class VideoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private VideoFactory videoFactory;

    @Autowired
    private MemberFactory memberFactory;


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

    @Test
    @WithMember("joohyuk")
    @DisplayName("동영상 업로드 성공")
    void uploadVideo_success() throws Exception {
        // given
        InputStream thumbnailResource = new ClassPathResource("images/twins.png").getInputStream();
        MockMultipartFile thumbnailImageFile
                = new MockMultipartFile(
                "thumbnailImageFile",
                "twins.png",
                "image/png",
                thumbnailResource
        );

        InputStream videoResource = new ClassPathResource("videos/aaa.mp4").getInputStream();
        MockMultipartFile videoFile
                = new MockMultipartFile(
                "videoFile",
                "test.mp4",
                "video/mp4",
                videoResource
        );
        String request = "/videos/upload";

        // when
        ResultActions resultActions = mockMvc.perform(multipart(request)
                .file(thumbnailImageFile)
                .file(videoFile)
                .param("title", "test title")
                .param("description", "test description")
                .with(csrf()));

        // then
        resultActions
                .andExpect(status().is3xxRedirection());

    }

    @WithMember("joohyuk")
    @DisplayName("동영상 업로드 실패")
    @ParameterizedTest(name = "{index} {displayName} title={0} description={1}")
    @CsvSource({"'d', 'asdfasdf'", "'test','test'", "'asdfasdf', ''", "'','adfas'"})
    void uploadVideo_fail(String title, String description) throws Exception {
        // given
        MockMultipartFile thumbnailImageFile
                = new MockMultipartFile(
                "thumbnailImageFile",
                "test.png",
                "image/png",
                "Hello, World!".getBytes()
        );

        MockMultipartFile videoFile
                = new MockMultipartFile(
                "videoFile",
                "test.mp4",
                "video/mp4",
                "Hello, World!".getBytes()
        );
        String request = "/videos/upload";

        // when
        ResultActions resultActions = mockMvc.perform(multipart(request)
                .file(thumbnailImageFile)
                .file(videoFile)
                .param("title", title)
                .param("description", description)
                .with(csrf()));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(model().attributeExists("member"))
                .andExpect(model().attributeExists("videoUploadForm"))
                .andExpect(view().name("videos/upload"));

    }

    @Test
    @WithMember("joohyuk")
    @DisplayName("동영상 상세화면")
    void videoDetailView() throws Exception {
        // given
        Member member = memberRepository.findByName("joohyuk").orElse(null);
        Video video = videoFactory.createVideo(member);

        String requestUri = "/videos/" + video.getId();

        // then
        ResultActions resultActions = mockMvc.perform(get(requestUri));

        // when
        resultActions
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("member"))
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(model().attributeExists("video"))
                .andExpect(view().name("videos/videoDetail"));
    }

    @Test
    @WithMember("joohyuk")
    @DisplayName("동영상 정보 수정 뷰 - 성공")
    void editVideoView_success() throws Exception {
        // given
        Member member = memberRepository.findByName("joohyuk").orElse(null);
        Video video = videoFactory.createVideo(member);

        String requestUri = "/videos/" + video.getId() + "/edit";

        // then
        ResultActions resultActions = mockMvc.perform(get(requestUri));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(model().attribute("id", video.getId()))
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(model().attributeExists("videoUpdateForm"))
                .andExpect(model().attributeExists("member"))
                .andExpect(view().name("videos/editVideo"));

    }

    @Test
    @WithMember("joohyuk")
    @DisplayName("동영상 정보 수정 뷰 - 실패(권한 X)")
    void editVideoView_fail() throws Exception {
        // given
        Member member = memberFactory.createMember("tigers");
        Video video = videoFactory.createVideo(member);

        String requestUri = "/videos/" + video.getId() + "/edit";

        // then
        ResultActions resultActions = mockMvc.perform(get(requestUri));

        // then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/videos/" + video.getId()));

    }

    @Test
    @WithMember("joohyuk")
    @DisplayName("동영상 정보 수정 - 성공")
    void editVideo_success() throws Exception {
        // given
        Member member = memberRepository.findByName("joohyuk").orElse(null);
        Video video = videoFactory.createVideo(member);

        String requestUri = "/videos/" + video.getId() + "/edit";

        // then
        ResultActions resultActions = mockMvc.perform(put(requestUri)
                .param("title", "update title test")
                .param("description", "update description test")
                .with(csrf()));

        // when
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/videos/" + video.getId()));

    }

    @ParameterizedTest(name = "{index} {displayName} title={0} description={1}")
    @CsvSource({"'', 'test description'", "'test title', 'test'"})
    @WithMember("joohyuk")
    @DisplayName("동영상 정보 수정 - 실패")
    void editVideo_fail(String title, String description) throws Exception {
        // given
        Member member = memberRepository.findByName("joohyuk").orElse(null);
        Video video = videoFactory.createVideo(member);

        String requestUri = "/videos/" + video.getId() + "/edit";

        // then
        ResultActions resultActions = mockMvc.perform(put(requestUri)
                .param("title", title)
                .param("description", description)
                .with(csrf()));

        // when
        resultActions
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("member"))
                .andExpect(model().attributeExists("pageTitle"))
                .andExpect(model().attributeExists("videoUploadForm"))
                .andExpect(view().name("videos/editVideo"));

    }

    @Test
    @WithMember("joohyuk")
    @DisplayName("동영상 삭제 - 성공")
    void deleteVideo_success() throws Exception {
        // given
        Member member = memberRepository.findByName("joohyuk").orElse(null);
        Video video = videoFactory.createVideo(member);

        String requestUri = "/videos/" + video.getId() + "/delete";

        // when
        ResultActions resultActions = mockMvc.perform(get(requestUri).with(csrf()));

        // then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("message"))
                .andExpect(redirectedUrl("/"));

    }

    @Test
    @WithMember("joohyuk")
    @DisplayName("동영상 삭제 - 실패(삭제 권한 X)")
    void deleteVideo_fail() throws Exception {
        // given
        Member member = memberFactory.createMember("tigers");
        Video video = videoFactory.createVideo(member);

        String requestUri = "/videos/" + video.getId() + "/delete";

        // when
        ResultActions resultActions = mockMvc.perform(get(requestUri).with(csrf()));

        // then
        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeExists("message"))
                .andExpect(redirectedUrl("/videos/" + video.getId()));

    }
}