package me.weekbelt.wetube.modules.video.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.weekbelt.wetube.modules.video.Video;

import java.util.Optional;

import static me.weekbelt.wetube.modules.member.QMember.member;
import static me.weekbelt.wetube.modules.video.QVideo.*;

@RequiredArgsConstructor
public class VideoRepositoryCustomImpl implements VideoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Video> findWithMemberById(Long id) {
        return Optional.ofNullable( queryFactory
                .selectFrom(video)
                .join(video.member, member).fetchJoin()
                .where(video.id.eq(id))
                .fetchOne());
    }
}
