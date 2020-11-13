package me.weekbelt.wetube.modules.comment.form;

import lombok.*;
import me.weekbelt.wetube.modules.member.UserMember;

import java.time.LocalDateTime;

@Builder @NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class CommentReadForm {

    private Long id;

    private Long videoId;

    private String videoTitle;

    private String name;

    private String text;

    private LocalDateTime createdDateTime;

    private LocalDateTime modifiedDateTime;

    public boolean isWriter(UserMember userMember) {
        String name = userMember.getMember().getName();
        return this.name.equals(name);
    }
}
