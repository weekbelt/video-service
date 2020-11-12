package me.weekbelt.wetube.modules.video.controller;

import lombok.RequiredArgsConstructor;
import me.weekbelt.wetube.modules.video.Video;
import me.weekbelt.wetube.modules.video.form.VideoElementForm;
import me.weekbelt.wetube.modules.video.repository.VideoRepository;
import me.weekbelt.wetube.modules.video.service.VideoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoApiController {

    @Value("${property.video.url}")
    private String VIDEO_PATH;
    @Value("${property.image.url}")
    private String IMAGE_PATH;

    private final VideoService videoService;
    private final VideoRepository videoRepository;

    @GetMapping
    public ResponseEntity<?> getVideos(@PageableDefault(size = 12, sort = "createdDate",
            direction = Sort.Direction.DESC, page = 0) Pageable pageable, @RequestParam(defaultValue = "") String keyword) {

        Page<VideoElementForm> videoElementFormPage = videoService.findVideoElementFormPageByKeyword(keyword, pageable);
        return ResponseEntity.ok().body(videoElementFormPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVideoDetail(@PathVariable Long id) {
        Video video = videoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 동영상이 존재하지 않습니다. videoId=" + id));

        File fileVideo = new File(VIDEO_PATH + video.getVideoFile().getSaveFileName());
        long fileLength = fileVideo.length();
        String fileUrl = VIDEO_PATH + video.getVideoFile().getSaveFileName();
        String[] contentType = {"video/mp4", "video/webm"};

        return setResponseVideo(fileLength, fileUrl, contentType);
    }

    @GetMapping("/{id}/thumbnail")
    public ResponseEntity<?> getThumbnailImage(@PathVariable Long id) {
        Video video = videoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 동영상이 존재하지 않습니다. videoId=" + id));

        File fileImage = new File(IMAGE_PATH + video.getImageFile().getSaveFileName());
        long fileLength = fileImage.length();
        String fileUrl = IMAGE_PATH + video.getImageFile().getSaveFileName();
        String[] contentType = {"image/jpg", "image/png", "image/jpeg"};

        return setResponseVideo(fileLength, fileUrl, contentType);
    }

    private ResponseEntity<?> setResponseVideo(long fileLength, String fileUrl, String[] contentType) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileLength))
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(new FileSystemResource(fileUrl));
    }
}
