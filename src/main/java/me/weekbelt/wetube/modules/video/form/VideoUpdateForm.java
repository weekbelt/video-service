package me.weekbelt.wetube.modules.video.form;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Builder @NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class VideoUpdateForm {


    @NotBlank
    @Length(min = 2, max = 30)
    private String title;


    @NotBlank
    @Length(min = 5, message = "5자 이상 입력해 주세요.")
    private String description;

    @Override
    public String toString() {
        return "VideoUpdateForm{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
