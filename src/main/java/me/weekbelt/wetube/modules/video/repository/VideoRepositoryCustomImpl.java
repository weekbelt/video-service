package me.weekbelt.wetube.modules.video.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.weekbelt.wetube.modules.video.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static me.weekbelt.wetube.modules.member.QMember.member;
import static me.weekbelt.wetube.modules.video.QVideo.*;

@RequiredArgsConstructor
public class VideoRepositoryCustomImpl implements VideoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Video> findAllPageByKeyword(String keyword, Pageable pageable) {
        QueryResults<Video> videoQueryResults = queryFactory
                .selectFrom(video)
                .join(video.member, member).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(containKeyword(keyword))
                .orderBy(video.createdDate.desc())
                .fetchResults();

        List<Video> boardList = videoQueryResults.getResults();
        return new PageImpl<>(boardList, pageable, videoQueryResults.getTotal());
    }

    @Override
    public Page<Video> findAllPageByName(String name, Pageable pageable) {
        QueryResults<Video> videoQueryResults = queryFactory
                .selectFrom(video)
                .join(video.member, member).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(member.name.eq(name))
                .orderBy(video.createdDate.desc())
                .fetchResults();
        List<Video> boardList = videoQueryResults.getResults();
        return new PageImpl<>(boardList, pageable, videoQueryResults.getTotal());
    }

    private BooleanExpression containKeyword(String keywordCond) {
        return video.title.containsIgnoreCase(keywordCond);
    }

    @Override
    public Optional<Video> findWithMemberById(Long id) {
        return Optional.ofNullable(queryFactory
                .selectFrom(video)
                .join(video.member, member).fetchJoin()
                .where(video.id.eq(id))
                .fetchOne());
    }
}
