package me.weekbelt.wetube.modules.comment.repository;

import me.weekbelt.wetube.modules.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepositoryCustom {

    Page<Comment> findAllByName(String name, Pageable pageable);
}
