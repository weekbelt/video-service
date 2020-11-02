package me.weekbelt.wetube.modules.video.controller;

import lombok.RequiredArgsConstructor;
import me.weekbelt.wetube.modules.video.Video;
import me.weekbelt.wetube.modules.video.repository.VideoRepository;
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
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoApiController {

    @Value("${property.video.url}")
    private String VIDEO_PATH;
    @Value("${property.image.url}")
    private String THUMB_PATH;

    private final VideoRepository videoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getVideoDetail(@PathVariable Long id) {
        Video video = videoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 동영상이 존재하지 않습니다. videoId=" + id));

        File fileVideo = new File(VIDEO_PATH + video.getVideoSaveFileName());
        long fileLength = fileVideo.length();
        String fileUrl = VIDEO_PATH + video.getVideoSaveFileName();
        String[] contentType = {"video/mp4", "video/webm"};

        return setResponseVideo(fileLength, fileUrl, contentType);
    }

    @GetMapping("/{id}/thumbnail")
    public ResponseEntity<?> getThumbnailImage(@PathVariable Long id) {
        Video video = videoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 동영상이 존재하지 않습니다. videoId=" + id));

        File fileImage = new File(THUMB_PATH + video.getThumbnailSaveFileName());
        long fileLength = fileImage.length();
        String fileUrl = THUMB_PATH + video.getThumbnailSaveFileName();
        String[] contentType = {"image/jpg", "image/png"};

        return setResponseVideo(fileLength, fileUrl, contentType);
    }

    private ResponseEntity<?> setResponseVideo(long fileLength, String fileUrl, String[] contentType) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileLength))
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(new FileSystemResource(fileUrl));
    }
}
