package me.weekbelt.wetube.modules.video;

import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.member.form.Creator;
import me.weekbelt.wetube.modules.video.form.VideoForm;
import me.weekbelt.wetube.modules.video.form.VideoUploadForm;

public class VideoDtoFactory {


    public static VideoForm videoToVideoForm(Video video, Creator creator) {
        return VideoForm.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .views(0L)
                .videoFile(video.getFileUrl())
                .creator(creator)
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
