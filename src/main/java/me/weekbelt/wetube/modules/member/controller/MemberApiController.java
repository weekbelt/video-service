package me.weekbelt.wetube.modules.member.controller;

import lombok.RequiredArgsConstructor;
import me.weekbelt.wetube.modules.comment.form.CommentReadForm;
import me.weekbelt.wetube.modules.comment.service.CommentService;
import me.weekbelt.wetube.modules.member.CurrentMember;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.member.repository.MemberRepository;
import me.weekbelt.wetube.modules.video.form.VideoElementForm;
import me.weekbelt.wetube.modules.video.service.VideoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {

    @Value("${property.image.url}")
    private String IMAGE_PATH;

    private final MemberRepository memberRepository;
    private final VideoService videoService;
    private final CommentService commentService;

    @GetMapping("/{name}/videos")
    public ResponseEntity<?> getVideosByName(@PageableDefault(size = 12, sort = "createdDate",
            direction = Sort.Direction.DESC, page = 0) Pageable pageable, @PathVariable String name) {
        Page<VideoElementForm> videoElementFormPage = videoService.findVideoElementFormPageByName(name, pageable);
        return ResponseEntity.ok().body(videoElementFormPage);
    }

    @GetMapping("/{name}/comments")
    public ResponseEntity<?> getCommentsByName(@PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable,
                                               @PathVariable String name) {
        Page<CommentReadForm> comments = commentService.findCommentsByName(name, pageable);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{name}/profileImage")
    public ResponseEntity<?> getMemberProfileImage(@PathVariable String name) {
        Member member = memberRepository.findByName(name).orElseThrow(
                () -> new IllegalArgumentException("찾는 이용자가 없습니다."));

        String fileUrl = IMAGE_PATH + member.getProfileImage().getSaveFileName();
        File profileImage = new File(fileUrl);
        long contentLength = profileImage.length();
        String[] contentType = {"image/png", "image/jpeg", "image/jpg"};

        return setResponseFile(fileUrl, contentLength, contentType);
    }

    private ResponseEntity<?> setResponseFile(String fileUrl, long contentLength, String[] contentType) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength))
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(new FileSystemResource(fileUrl));
    }
}
