package me.weekbelt.wetube.modules.member.controller;

import lombok.RequiredArgsConstructor;
import me.weekbelt.wetube.modules.member.CurrentMember;
import me.weekbelt.wetube.modules.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/{name}")
    public String userDetail(@CurrentMember Member member, @PathVariable String name, Model model) {
        model.addAttribute("pageTitle", "Member Detail");
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
