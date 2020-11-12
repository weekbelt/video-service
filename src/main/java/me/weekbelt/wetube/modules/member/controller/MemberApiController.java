package me.weekbelt.wetube.modules.member.controller;

import lombok.RequiredArgsConstructor;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
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
