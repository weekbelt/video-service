package me.weekbelt.wetube.modules.video.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.weekbelt.wetube.modules.member.form.Creator;

@Getter @Setter
public class VideoElementForm {

    private Long id;
    private String title;
    private String description;
    private Long views;
    private String videoFile;
    private Creator creator;

    @Builder
    public VideoElementForm(Long id, String title, String description, Long views, String videoFile, Creator creator) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.views = views;
        this.videoFile = videoFile;
        this.creator = creator;
    }
}
