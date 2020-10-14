package me.weekbelt.wetube.modules.comment;

import me.weekbelt.wetube.modules.comment.form.CommentReadForm;

public class CommentDtoFactory {
    public static CommentReadForm commentToCommentReadForm(Comment comment) {
        return CommentReadForm.builder()
                .text(comment.getText())
                .createdAt(comment.getCreatedDate())
                .build();
    }
}
