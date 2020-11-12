package me.weekbelt.wetube.modules.video.service;

import lombok.RequiredArgsConstructor;
import me.weekbelt.wetube.infra.util.FileUtils;
import me.weekbelt.wetube.modules.FileInfo.FileInfo;
import me.weekbelt.wetube.modules.FileInfo.repository.FileInfoRepository;
import me.weekbelt.wetube.modules.comment.Comment;
import me.weekbelt.wetube.modules.comment.CommentDtoFactory;
import me.weekbelt.wetube.modules.comment.form.CommentReadForm;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.member.MemberDtoFactory;
import me.weekbelt.wetube.modules.member.repository.MemberRepository;
import me.weekbelt.wetube.modules.video.VideoDtoFactory;
import me.weekbelt.wetube.modules.member.form.Creator;
import me.weekbelt.wetube.modules.video.Video;
import me.weekbelt.wetube.modules.video.form.*;
import me.weekbelt.wetube.modules.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final MemberRepository memberRepository;
    private final FileInfoRepository fileInfoRepository;
    private final FileUtils fileUtils;

    public Page<VideoElementForm> findVideoElementFormPageByKeyword(String keyword, Pageable pageable) {
        Page<Video> videoPage = videoRepository.findAllPageByKeyword(keyword, pageable);

        return videoPage.map(video -> {
            Creator creator = MemberDtoFactory.memberToCreator(video.getMember());
            return VideoDtoFactory.videoToVideoElementForm(video, creator);
        });
    }

    public VideoReadForm findVideoForm(Long videoId) {
        Video findVideo = videoRepository.findWithMemberById(videoId).orElseThrow(
                () -> new IllegalArgumentException("찾는 비디오가 없습니다. Video Id=" + videoId));

        Member findMember = findVideo.getMember();
        List<Comment> comments = findVideo.getComments();

        Creator creator = MemberDtoFactory.memberToCreator(findMember);
        List<CommentReadForm> commentReadForms = comments.stream()
                .map(CommentDtoFactory::commentToCommentReadForm)
                .collect(Collectors.toList());

        return VideoDtoFactory.videoToVideoReadForm(findVideo, creator, commentReadForms);
    }

    public VideoReadForm uploadVideo(Member member, VideoUploadForm videoUploadForm) {
        Member findMember = memberRepository.findByName(member.getName()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. 유저 이름=" + member.getName()));

        // 로컬에 Thumbnail, Video 저장
        FileInfo imageFile = fileUtils.saveFileAtLocal(videoUploadForm.getThumbnailImageFile());
        FileInfo videoFile = fileUtils.saveFileAtLocal(videoUploadForm.getVideoFile());

        // DB에 Thumbnail, Video 정보 저장
        FileInfo savedImageFile = fileInfoRepository.save(imageFile);
        FileInfo savedVideoFile = fileInfoRepository.save(videoFile);

        Video video = VideoDtoFactory.videoUploadFormToVideo(videoUploadForm, findMember, savedVideoFile, savedImageFile);

        Video savedVideo = videoRepository.save(video);
        Creator creator = MemberDtoFactory.memberToCreator(findMember);
        return VideoDtoFactory.videoToVideoReadForm(savedVideo, creator, new ArrayList<>());
    }


    public void updateVideo(Long videoId, VideoUpdateForm videoUpdateForm) {
        Video findVideo = videoRepository.findById(videoId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 비디오가 없습니다. video id=" + videoId));
        findVideo.update(videoUpdateForm);
    }

    public void deleteVideo(Long id) {
        Video findVideo = videoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("삭제하고자하는 비디오가 없습니다. videoId=" + id));
        videoRepository.delete(findVideo);
    }

    public void plusView(Long videoId) {
        Video findVideo = videoRepository.findById(videoId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 동영상이 존재하지 않습니다. videoId=" + videoId));

        // 조회수 증가
        findVideo.plusView();
        videoRepository.save(findVideo);
    }
}
