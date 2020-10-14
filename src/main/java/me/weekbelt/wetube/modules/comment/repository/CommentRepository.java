package me.weekbelt.wetube.modules.comment.repository;

import me.weekbelt.wetube.modules.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByVideoIdOrderByIdDesc(Long videoId);
}
