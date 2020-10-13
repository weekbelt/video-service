package me.weekbelt.wetube.modules;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.member.form.Creator;
import me.weekbelt.wetube.modules.member.form.MemberJoinForm;
import me.weekbelt.wetube.modules.member.service.MemberService;
import me.weekbelt.wetube.modules.member.validator.MemberJoinFormValidator;
import me.weekbelt.wetube.modules.video.form.VideoForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;

    ArrayList<VideoForm> videos = new ArrayList<>();

    @InitBinder("memberJoinForm")
    public void memberJoinFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new MemberJoinFormValidator());
    }

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
                .videoFile("https://archive.org/download/BigBuckBunny_124/Content/big_buck_bunny_720p_surround.mp4")
                .creator(creator)
                .build();
        VideoForm video2 = VideoForm.builder()
                .id(1212121L)
                .title("Video super")
                .description("This is something I love")
                .views(24L)
                .videoFile("https://archive.org/download/BigBuckBunny_124/Content/big_buck_bunny_720p_surround.mp4")
                .creator(creator)
                .build();
        VideoForm video3 = VideoForm.builder()
                .id(55555L)
                .title("Video nice")
                .description("This is something I love")
                .views(24L)
                .videoFile("https://archive.org/download/BigBuckBunny_124/Content/big_buck_bunny_720p_surround.mp4")
                .creator(creator)
                .build();
        VideoForm video4 = VideoForm.builder()
                .id(11111L)
                .title("Video perfect")
                .description("This is something I love")
                .views(24L)
                .videoFile("https://archive.org/download/BigBuckBunny_124/Content/big_buck_bunny_720p_surround.mp4")
                .creator(creator)
                .build();
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
        model.addAttribute("videos", videos);
        return "videos/search";
    }

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("memberJoinForm", new MemberJoinForm());
        model.addAttribute("pageTitle", "Join");
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid MemberJoinForm memberJoinForm, Errors errors) {
        if (errors.hasErrors()) {
            return "join";
        }

        Member member = memberService.processNewMember(memberJoinForm);
        memberService.login(member);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("pageTitle", "Log In");
        return "login";
    }
}
