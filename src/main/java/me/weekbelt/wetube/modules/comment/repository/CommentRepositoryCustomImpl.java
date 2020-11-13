package me.weekbelt.wetube.modules.comment.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.weekbelt.wetube.modules.comment.Comment;
import me.weekbelt.wetube.modules.comment.QComment;
import me.weekbelt.wetube.modules.member.QMember;
import me.weekbelt.wetube.modules.video.QVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static me.weekbelt.wetube.modules.comment.QComment.comment;
import static me.weekbelt.wetube.modules.member.QMember.member;
import static me.weekbelt.wetube.modules.video.QVideo.video;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Comment> findAllByName(String name, Pageable pageable) {
        QueryResults<Comment> commentQueryResults = queryFactory
                .selectFrom(comment)
                .join(comment.video, video).fetchJoin()
                .join(comment.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(member.name.eq(name))
                .orderBy(comment.createdDate.desc())
                .fetchResults();

        List<Comment> commentList = commentQueryResults.getResults();
        return new PageImpl<>(commentList, pageable, commentQueryResults.getTotal());
    }
}
