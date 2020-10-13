package me.weekbelt.wetube.modules.video.controller;

import lombok.RequiredArgsConstructor;
import me.weekbelt.wetube.modules.member.CurrentMember;
import me.weekbelt.wetube.modules.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {

    @GetMapping("/upload")
    public String uploadVideo(@CurrentMember Member member, Model model) {
        model.addAttribute("pageTitle", "Upload");
        return "videos/upload";
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
