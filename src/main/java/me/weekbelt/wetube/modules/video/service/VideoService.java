package me.weekbelt.wetube.modules.video.service;

import lombok.RequiredArgsConstructor;
import me.weekbelt.wetube.infra.util.VideoFile;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.member.MemberDtoFactory;
import me.weekbelt.wetube.modules.member.repository.MemberRepository;
import me.weekbelt.wetube.modules.video.VideoDtoFactory;
import me.weekbelt.wetube.modules.member.form.Creator;
import me.weekbelt.wetube.modules.video.Video;
import me.weekbelt.wetube.modules.video.form.VideoForm;
import me.weekbelt.wetube.modules.video.form.VideoUpdateForm;
import me.weekbelt.wetube.modules.video.form.VideoUploadForm;
import me.weekbelt.wetube.modules.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoService {

    @Value("${property.video.url}")
    private String PATH;
    @Value("${property.video.uploadFolder}")
    private String UPLOAD_VIDEO;

    private final VideoRepository videoRepository;
    private final MemberRepository memberRepository;

    public List<VideoForm> findVideoForms() {
        List<Video> videoList = videoRepository.findAll();
        return videoList.stream().map(video -> {
            Member createMember = video.getMember();
            Creator creator = MemberDtoFactory.memberToCreator(createMember);

            return VideoDtoFactory.videoToVideoForm(video, creator);
        }).collect(Collectors.toList());
    }

    public VideoForm findVideoForm(Long videoId, Member member) {
        Member findMember = memberRepository.findByName(member.getName()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. name=" + member.getName()));

        Creator creator = MemberDtoFactory.memberToCreator(findMember);
        Video findVideo = videoRepository.findById(videoId).orElseThrow(
                () -> new IllegalArgumentException("찾는 비디오가 없습니다. Video Id=" + videoId));

        return VideoDtoFactory.videoToVideoForm(findVideo, creator);
    }

    public VideoForm uploadVideo(Member member, VideoUploadForm videoUploadForm) {
        Member findMember = memberRepository.findByName(member.getName()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. 유저 이름=" + member.getName()));

        // 로컬에 Video 저장
        MultipartFile videoMultipartFile = videoUploadForm.getFile();
        String fileName = VideoFile.makeInherenceFile(videoMultipartFile.getOriginalFilename());
        String saveFileName = UPLOAD_VIDEO + fileName;
        VideoFile.saveVideo(videoMultipartFile, PATH + saveFileName);

        // DB에 Video 저장
        Video video = VideoDtoFactory.videoUploadFormToVideo(videoUploadForm, findMember, saveFileName);

        Video savedVideo = videoRepository.save(video);
        Creator creator = MemberDtoFactory.memberToCreator(findMember);
        return VideoDtoFactory.videoToVideoForm(savedVideo, creator);
    }

    public void updateVideo(Long videoId, VideoUpdateForm videoUpdateForm) {
        Video findVideo = videoRepository.findById(videoId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 비디오가 없습니다. video id=" + videoId));
        findVideo.update(videoUpdateForm);
    }
}
