package me.weekbelt.wetube.modules.video;

import me.weekbelt.wetube.modules.comment.form.CommentReadForm;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.member.form.Creator;
import me.weekbelt.wetube.modules.video.form.VideoElementForm;
import me.weekbelt.wetube.modules.video.form.VideoReadForm;
import me.weekbelt.wetube.modules.video.form.VideoUploadForm;

import java.util.List;

public class VideoDtoFactory {

    public static VideoElementForm videoToVideoElementForm(Video video, Creator creator) {
       return VideoElementForm.builder()
               .id(video.getId())
               .title(video.getTitle())
               .description(video.getDescription())
               .views(0L)
               .videoFile(video.getFileUrl())
               .creator(creator)
               .build();
    }

    public static VideoReadForm videoToVideoReadForm(Video video, Creator creator, List<CommentReadForm> commentReadForms) {
        return VideoReadForm.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .views(0L)
                .videoFile(video.getFileUrl())
                .creator(creator)
                .comments(commentReadForms)
                .build();
    }

    public static Video videoUploadFormToVideo(VideoUploadForm videoUploadForm, Member member, String fileUrl) {
        return Video.builder()
                .title(videoUploadForm.getTitle())
                .description(videoUploadForm.getDescription())
                .fileUrl(fileUrl)
                .views(0L)
                .member(member)
                .build();
    }
}
