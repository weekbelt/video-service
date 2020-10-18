package me.weekbelt.wetube.modules.member;

import me.weekbelt.wetube.modules.member.form.Creator;
import me.weekbelt.wetube.modules.member.form.MemberJoinForm;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MemberDtoFactory {
    public static Creator memberToCreator(Member member) {
        return Creator.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }

    public static Member memberJoinFormToMember(MemberJoinForm memberJoinForm,
                                                PasswordEncoder passwordEncoder) {
        if (memberJoinForm.getName().equals("weekbelt")) {
            return Member.builder()
                    .email(memberJoinForm.getEmail())
                    .name(memberJoinForm.getName())
                    .password(passwordEncoder.encode(memberJoinForm.getPassword()))
                    .role(Role.ADMIN)
                    .build();
        } else {
            return Member.builder()
                    .email(memberJoinForm.getEmail())
                    .name(memberJoinForm.getName())
                    .password(passwordEncoder.encode(memberJoinForm.getPassword()))
                    .role(Role.USER)
                    .build();
        }
    }
}
