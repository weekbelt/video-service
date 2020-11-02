package me.weekbelt.wetube.modules.video.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.weekbelt.wetube.modules.member.form.Creator;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoElementForm {

    private Long id;
    private String title;
    private String description;
    private Long views;
    private Creator creator;

    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;

    @Builder
    public VideoElementForm(Long id, String title, String description, Long views,
                            Creator creator, LocalDateTime createdDateTime, LocalDateTime modifiedDateTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.views = views;
        this.creator = creator;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }
}
