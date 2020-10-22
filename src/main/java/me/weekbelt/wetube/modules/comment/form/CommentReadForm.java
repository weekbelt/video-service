package me.weekbelt.wetube.modules.comment.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter @Setter
public class CommentReadForm {

    private Long id;

    private Long videoId;

    private String name;

    private String text;

    private LocalDateTime createdAt;

}
