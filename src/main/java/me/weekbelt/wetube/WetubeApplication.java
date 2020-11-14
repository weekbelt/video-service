package me.weekbelt.wetube;

import me.weekbelt.wetube.modules.comment.form.CommentCreateForm;
import me.weekbelt.wetube.modules.comment.service.CommentService;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.member.form.MemberJoinForm;
import me.weekbelt.wetube.modules.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WetubeApplication {

    @Autowired
    private MemberService memberService;
    @Autowired
    private CommentService commentService;

    public static void main(String[] args) {
        SpringApplication.run(WetubeApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        MemberJoinForm memberJoinForm = new MemberJoinForm();
        memberJoinForm.setName("Skywalker");
        memberJoinForm.setEmail("vfrvfr4207@gmail.com");
        memberJoinForm.setPassword("12345678");
        memberJoinForm.setPassword2("12345678");
        Member member = memberService.processNewMember(memberJoinForm);

        // 배포시 지워야 할 데이터
        for (int i = 1; i <= 27; i++) {
            CommentCreateForm commentCreateForm = CommentCreateForm.builder().text("댓글 남겨요." + i).build();
            commentService.addComment(1L, member.getId(), commentCreateForm);
        }
    }
}
