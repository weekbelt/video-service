package me.weekbelt.wetube.modules.comment;

import me.weekbelt.wetube.modules.comment.form.CommentReadForm;

import java.util.List;
import java.util.stream.Collectors;

public class CommentDtoFactory {
    public static CommentReadForm commentToCommentReadForm(Comment comment) {
        return CommentReadForm.builder()
                .text(comment.getText())
                .createdAt(comment.getCreatedDate())
                .build();
    }

    public static List<CommentReadForm> commentToCommentReadForms(List<Comment> comments) {
        return comments.stream().map(CommentDtoFactory::commentToCommentReadForm)
                .collect(Collectors.toList());
    }
}
