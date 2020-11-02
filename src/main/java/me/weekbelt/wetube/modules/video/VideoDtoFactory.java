package me.weekbelt.wetube.modules.video;

import me.weekbelt.wetube.modules.comment.form.CommentReadForm;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.member.MemberDtoFactory;
import me.weekbelt.wetube.modules.member.form.Creator;
import me.weekbelt.wetube.modules.video.form.VideoElementForm;
import me.weekbelt.wetube.modules.video.form.VideoReadForm;
import me.weekbelt.wetube.modules.video.form.VideoUpdateForm;
import me.weekbelt.wetube.modules.video.form.VideoUploadForm;

import java.util.List;
import java.util.stream.Collectors;

public class VideoDtoFactory {
    public static VideoElementForm videoToVideoElementForm(Video video, Creator creator) {
       return VideoElementForm.builder()
               .id(video.getId())
               .title(video.getTitle())
               .description(video.getDescription())
               .views(video.getViews())
               .creator(creator)
               .createdDateTime(video.getCreatedDate())
               .modifiedDateTime(video.getLastModifiedDate())
               .build();
    }

    public static VideoReadForm videoToVideoReadForm(Video video, Creator creator, List<CommentReadForm> commentReadForms) {
        return VideoReadForm.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .views(video.getViews())
                .saveFileName(video.getVideoSaveFileName())
                .creator(creator)
                .comments(commentReadForms)
                .build();
    }

    public static Video videoUploadFormToVideo(VideoUploadForm videoUploadForm, Member member,
                                               String thumbnailSaveFileName, String videoSaveFileName) {
        return Video.builder()
                .title(videoUploadForm.getTitle())
                .description(videoUploadForm.getDescription())
                .thumbnailSaveFileName(thumbnailSaveFileName)
                .videoSaveFileName(videoSaveFileName)
                .views(0L)
                .member(member)
                .build();
    }

    public static VideoUpdateForm videoToVideoUpdateForm(Video video) {
        return VideoUpdateForm.builder()
                .title(video.getTitle())
                .description(video.getDescription())
                .build();
    }

    public static List<VideoElementForm> videosToVideoElementForms(List<Video> videoList) {
        return videoList.stream().map(video -> {
            Member createMember = video.getMember();
            Creator creator = MemberDtoFactory.memberToCreator(createMember);
            return VideoDtoFactory.videoToVideoElementForm(video, creator);
        }).collect(Collectors.toList());

    }
}
