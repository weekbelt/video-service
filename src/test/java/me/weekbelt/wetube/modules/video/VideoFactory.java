package me.weekbelt.wetube.modules.video;

import lombok.RequiredArgsConstructor;
import me.weekbelt.wetube.infra.util.FileUtils;
import me.weekbelt.wetube.modules.FileInfo.FileInfo;
import me.weekbelt.wetube.modules.FileInfo.repository.FileInfoRepository;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.video.form.VideoUploadForm;
import me.weekbelt.wetube.modules.video.repository.VideoRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoFactory {

    private final VideoRepository videoRepository;
    private final FileInfoRepository fileInfoRepository;

    public Video createVideo(Member member) {
        FileInfo videoFile = FileInfo.builder()
                .fileName("test.mp4")
                .saveFileName("/upload_video")
                .contentType("video/mp4")
                .build();

        FileInfo imageFile = FileInfo.builder()
                .fileName("test.png")
                .saveFileName("/upload_image")
                .contentType("image/png")
                .build();

        FileInfo savedVideoFile = fileInfoRepository.save(videoFile);
        FileInfo savedImageFile = fileInfoRepository.save(imageFile);

        Video video = Video.builder()
                .member(member)
                .title("test video")
                .description("test video description")
                .views(0L)
                .videoFile(savedVideoFile)
                .imageFile(savedImageFile)
                .build();

        return videoRepository.save(video);
    }
}

