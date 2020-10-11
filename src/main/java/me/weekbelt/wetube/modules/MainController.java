package me.weekbelt.wetube.modules;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/search")
    public String searchVideo() {
        return "videos/search";
    }

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
