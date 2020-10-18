package me.weekbelt.wetube.modules;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.weekbelt.wetube.modules.member.CurrentMember;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.member.form.MemberJoinForm;
import me.weekbelt.wetube.modules.member.service.MemberService;
import me.weekbelt.wetube.modules.member.validator.MemberJoinFormValidator;
import me.weekbelt.wetube.modules.video.form.VideoElementForm;
import me.weekbelt.wetube.modules.video.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final MemberService memberService;
    private final VideoService videoService;
    private final MemberJoinFormValidator memberJoinFormValidator;

    @InitBinder("memberJoinForm")
    public void memberJoinFormInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(memberJoinFormValidator);
    }

    @GetMapping("/")
    public String mainPage(@CurrentMember Member member, Model model) {
        if (member != null) {
            model.addAttribute("member", member);
        }
        List<VideoElementForm> videos = videoService.findVideoElementForms();
        model.addAttribute("videos", videos);
        model.addAttribute("pageTitle", "Home");
        return "home";
    }

    // TODO: 검색기능 개선
    @GetMapping("/search")
    public String searchVideo(@RequestParam String keyword, Model model) {
        model.addAttribute("pageTitle", "Search");
        model.addAttribute("searchingBy", keyword);
        List<VideoElementForm> videos = videoService.findVideoElementFormsByKeyword(keyword);
        model.addAttribute("videos", videos);
        return "videos/search";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
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
