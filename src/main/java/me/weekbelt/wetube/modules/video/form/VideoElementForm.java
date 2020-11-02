package me.weekbelt.wetube.modules.video.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.weekbelt.wetube.modules.member.form.Creator;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class VideoElementForm {

    private Long id;
    private String title;
    private String description;
    private Long views;
    private String saveFileName;
    private Creator creator;

    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;

    @Builder
    public VideoElementForm(Long id, String title, String description, Long views,
                            String saveFileName, Creator creator,
                            LocalDateTime createdDateTime, LocalDateTime modifiedDateTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.views = views;
        this.saveFileName = saveFileName;
        this.creator = creator;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }
}
