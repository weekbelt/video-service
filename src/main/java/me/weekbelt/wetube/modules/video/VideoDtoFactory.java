package me.weekbelt.wetube.modules.video;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.weekbelt.wetube.modules.FileInfo.FileInfo;
import me.weekbelt.wetube.modules.comment.form.CommentReadForm;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.member.MemberDtoFactory;
import me.weekbelt.wetube.modules.member.form.Creator;
import me.weekbelt.wetube.modules.video.form.VideoElementForm;
import me.weekbelt.wetube.modules.video.form.VideoReadForm;
import me.weekbelt.wetube.modules.video.form.VideoUpdateForm;
import me.weekbelt.wetube.modules.video.form.VideoUploadForm;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

// 인스턴스화 방지
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VideoDtoFactory {

    public static List<VideoElementForm> videosToVideoElementForms(List<Video> videoList) {
        return videoList.stream().map(video -> {
            Member createMember = video.getMember();
            Creator creator = MemberDtoFactory.memberToCreator(createMember);
            return videoToVideoElementForm(video, creator);
        }).collect(Collectors.toList());
    }

    public static Page<VideoElementForm> videoPageToVideoElementFormPage(Page<Video> videoPage) {
        return videoPage.map(video -> {
            Creator creator = MemberDtoFactory.memberToCreator(video.getMember());
            return videoToVideoElementForm(video, creator);
        });
    }


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
                .saveFileName(video.getVideoFile().getSaveFileName())
                .creator(creator)
                .comments(commentReadForms)
                .build();
    }

    public static Video videoUploadFormToVideo(VideoUploadForm videoUploadForm, Member member,
                                               FileInfo videoFile, FileInfo imageFile) {
        return Video.builder()
                .title(videoUploadForm.getTitle())
                .description(videoUploadForm.getDescription())
                .videoFile(videoFile)
                .imageFile(imageFile)
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
}
