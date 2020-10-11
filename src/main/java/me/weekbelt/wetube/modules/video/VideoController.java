package me.weekbelt.wetube.modules.video;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {

    @GetMapping("/upload")
    public String uploadVideo() {
        return "videos/upload";
    }

    @GetMapping("/{id}")
    public String editVideo(@PathVariable Long id) {
        return "videos/editVideo";
    }

}
