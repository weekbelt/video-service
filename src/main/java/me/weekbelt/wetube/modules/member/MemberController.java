package me.weekbelt.wetube.modules.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/{id}")
    public String userDetail(@PathVariable Long id, Model model) {
        model.addAttribute("pageTitle", "Member Detail");
        return "users/userDetail";
    }

    @GetMapping("/edit-profile")
    public String editProfile(Model model) {
        model.addAttribute("pageTitle", "Edit Profile");
        return "users/editProfile";
    }

    @GetMapping("/change-password")
    public String changePassword(Model model) {
        model.addAttribute("pageTitle", "Change Password");
        return "users/changePassword";
    }
}
