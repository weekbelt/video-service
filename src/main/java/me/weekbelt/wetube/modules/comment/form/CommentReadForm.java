package me.weekbelt.wetube.modules.comment.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Builder
@Getter @Setter
public class CommentReadForm {

    @NotBlank
    @Length(min = 3, max = 200, message = "3자 이상 200자 미만으로 적어주세요.")
    private String text;

    private LocalDateTime createdAt;

}
