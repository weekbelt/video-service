package me.weekbelt.wetube.modules.video.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.weekbelt.wetube.modules.member.CurrentMember;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.video.Video;
import me.weekbelt.wetube.modules.video.form.VideoForm;
import me.weekbelt.wetube.modules.video.form.VideoUpdateForm;
import me.weekbelt.wetube.modules.video.form.VideoUploadForm;
import me.weekbelt.wetube.modules.video.service.VideoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Slf4j
@Controller
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {
//
//    @Value("${property.video.url}")
//    private String DIR;
//
    private final VideoService videoService;

    @GetMapping("/upload")
    public String uploadVideoForm(@CurrentMember Member member, Model model) {
        model.addAttribute("pageTitle", "Upload");
        model.addAttribute("videoUploadForm", new VideoUploadForm());
        return "videos/upload";
    }

    @PostMapping("/upload")
    public String uploadVideo(@CurrentMember Member member, VideoUploadForm videoUploadForm) {
        log.info(videoUploadForm.toString());
        VideoForm videoForm = videoService.uploadVideo(member, videoUploadForm);
        return "redirect:/videos/" + videoForm.getId();
    }

//    @GetMapping("/download")
//    public void getVideos(HttpServletRequest req, @RequestParam String fileUrl) {
//        log.info("fileUrl: " + fileUrl);
//    }

    @GetMapping("/{id}")
    public String videoDetail(@CurrentMember Member member, @PathVariable Long id, Model model) {
        VideoForm videoForm = videoService.findVideoForm(id, member);
        model.addAttribute("video", videoForm);
        model.addAttribute("pageTitle", videoForm.getTitle());
        return "videos/videoDetail";
    }

    @GetMapping("/{id}/edit")
    public String editVideoView(@CurrentMember Member member, @PathVariable Long id, Model model) {
        VideoForm videoForm = videoService.findVideoForm(id, member);
        model.addAttribute("video", videoForm);
        model.addAttribute("pageTitle", "Edit Video");
        model.addAttribute("videoUpdateForm", new VideoUploadForm());
        return "videos/editVideo";
    }

    @PutMapping("/{id}/edit")
    public String editVideo(@CurrentMember Member member, @PathVariable Long id,
                            VideoUpdateForm videoUpdateForm, Model model){
        videoService.updateVideo(id, videoUpdateForm);
        return "redirect:/videos/" + id;
    }

    @GetMapping("/{id}/delete")
    public String deleteVideo(@CurrentMember Member member, @PathVariable Long id, Model model) {
        videoService.deleteVideo(id);
        return "redirect:/";
    }


//    private ResponseEntity<Resource> setResponseVideo(long fileLength, String saveFileName, String contentType) {
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileLength))
//                .header(HttpHeaders.CONTENT_TYPE, contentType)
//                .body(new FileSystemResource(saveFileName));
//    }
}
