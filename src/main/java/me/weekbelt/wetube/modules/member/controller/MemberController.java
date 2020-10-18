package me.weekbelt.wetube.modules.member.controller;

import lombok.RequiredArgsConstructor;
import me.weekbelt.wetube.modules.member.CurrentMember;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/{name}")
    public String userDetail(@CurrentMember Member member, @PathVariable String name, Model model) {
        model.addAttribute("pageTitle", "Member Detail");
        model.addAttribute("member", member);
        return "users/userDetail";
    }

    @GetMapping("/edit-profile")
    public String editProfile(@CurrentMember Member member, Model model) {
        model.addAttribute("pageTitle", "Edit Profile");
        return "users/editProfile";
    }

    @GetMapping("/change-password")
    public String changePassword(@CurrentMember Member member, Model model) {
        model.addAttribute("pageTitle", "Change Password");
        return "users/changePassword";
    }
}
