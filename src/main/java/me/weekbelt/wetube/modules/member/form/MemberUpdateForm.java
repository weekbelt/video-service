package me.weekbelt.wetube.modules.member.form;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder @NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class MemberUpdateForm {

    private MultipartFile multipartImageProfile;
}
