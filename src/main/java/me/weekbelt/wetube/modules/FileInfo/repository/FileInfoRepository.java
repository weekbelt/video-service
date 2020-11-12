package me.weekbelt.wetube.modules.FileInfo.repository;

import me.weekbelt.wetube.modules.FileInfo.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
}
