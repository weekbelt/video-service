package me.weekbelt.wetube.modules.member;

import me.weekbelt.wetube.modules.member.form.Creator;

public class MemberDtoFactory {
    public static Creator memberToCreator(Member member) {
        return Creator.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }
}
