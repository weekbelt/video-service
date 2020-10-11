package me.weekbelt.wetube.modules;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }
}
