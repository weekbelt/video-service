package me.weekbelt.wetube.modules.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/{id}")
    public String userDetail(@PathVariable Long id) {
        return "users/userDetail";
    }

    @GetMapping("/edit-profile")
    public String editProfile() {
        return "users/editProfile";
    }

    @GetMapping("/change-password")
    public String changePassword() {
        return "users/changePassword";
    }
}
