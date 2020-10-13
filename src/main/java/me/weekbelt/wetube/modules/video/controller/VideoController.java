package me.weekbelt.wetube.modules.video.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.weekbelt.wetube.modules.member.CurrentMember;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.video.form.VideoUploadForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {

    @GetMapping("/upload")
    public String uploadVideoForm(@CurrentMember Member member, Model model) {
        model.addAttribute("pageTitle", "Upload");
        model.addAttribute("videoUploadForm", new VideoUploadForm());
        return "videos/upload";
    }

    @PostMapping("/upload")
    public String uploadVideo(@CurrentMember Member member, VideoUploadForm videoUploadForm) {
        // TODO: Upload and Save Video
        log.info(videoUploadForm.toString());
        return "redirect:/videos/324393";
    }

    @GetMapping("/{id}")
    public String videoDetail(@CurrentMember Member member, @PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Video Detail");
        return "videos/videoDetail";
    }

    @GetMapping("/{id}/edit")
    public String editVideo(@CurrentMember Member member, @PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Edit Video");
        return "videos/editVideo";
    }

    @GetMapping("/{id}/delete")
    public String deleteVideo(@CurrentMember Member member, @PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Delete Video");
        return "videos/deleteVideo";
    }


}
