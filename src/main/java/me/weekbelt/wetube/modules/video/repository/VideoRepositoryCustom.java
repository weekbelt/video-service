package me.weekbelt.wetube.modules.video.repository;

import me.weekbelt.wetube.modules.video.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VideoRepositoryCustom {
    Page<Video> findAllPageByKeyword(String keyword, Pageable pageable);

    Optional<Video> findWithMemberById(Long id);
}
