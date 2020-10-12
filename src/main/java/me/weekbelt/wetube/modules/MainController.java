package me.weekbelt.wetube.modules;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("pageTitle", "Home");
        return "index";
    }

    @GetMapping("/search")
    public String searchVideo(Model model) {
        model.addAttribute("pageTitle", "Search");
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
