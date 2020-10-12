package me.weekbelt.wetube.modules;

import lombok.RequiredArgsConstructor;
import me.weekbelt.wetube.modules.member.form.Creator;
import me.weekbelt.wetube.modules.video.form.VideoForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String mainPage(Model model) {

        // 임시데이터 시작
        Creator creator = Creator.builder()
                .id(121212L)
                .name("Nicolas")
                .email("nico@las.com")
                .build();

        VideoForm video1 = VideoForm.builder()
                .id(324393L)
                .title("Video awesome")
                .description("This is something I love")
                .views(24L)
                .videoFile("https://archive.org/details/BigBuckBunny_124")
                .creator(creator)
                .build();
        VideoForm video2 = VideoForm.builder()
                .id(1212121L)
                .title("Video super")
                .description("This is something I love")
                .views(24L)
                .videoFile("https://archive.org/details/BigBuckBunny_124")
                .creator(creator)
                .build();
        VideoForm video3 = VideoForm.builder()
                .id(55555L)
                .title("Video nice")
                .description("This is something I love")
                .views(24L)
                .videoFile("https://archive.org/details/BigBuckBunny_124")
                .creator(creator)
                .build();
        VideoForm video4 = VideoForm.builder()
                .id(11111L)
                .title("Video perfect")
                .description("This is something I love")
                .views(24L)
                .videoFile("https://archive.org/details/BigBuckBunny_124")
                .creator(creator)
                .build();
        ArrayList<VideoForm> videos = new ArrayList<>();
        videos.add(video1);
        videos.add(video2);
        videos.add(video3);
        videos.add(video4);
        model.addAttribute("videos", videos);
        // 임시데이터 끝

        model.addAttribute("pageTitle", "Home");
        return "home";
    }

    @GetMapping("/search")
    public String searchVideo(@RequestParam String term, Model model) {
        model.addAttribute("pageTitle", "Search");
        model.addAttribute("searchingBy", term);
        return "videos/search";
    }

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("pageTitle", "Join");
        return "join";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("pageTitle", "Log In");
        return "login";
    }
}
