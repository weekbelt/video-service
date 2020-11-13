package me.weekbelt.wetube;

import me.weekbelt.wetube.modules.member.form.MemberJoinForm;
import me.weekbelt.wetube.modules.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WetubeApplication implements CommandLineRunner {

    @Autowired
    private MemberService memberService;

    public static void main(String[] args) {
        SpringApplication.run(WetubeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        MemberJoinForm memberJoinForm = new MemberJoinForm();
        memberJoinForm.setName("Skywalker");
        memberJoinForm.setEmail("vfrvfr4207@gmail.com");
        memberJoinForm.setPassword("12345678");
        memberJoinForm.setPassword2("12345678");
        memberService.processNewMember(memberJoinForm);


    }
}
