package me.weekbelt.wetube.modules.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.wetube.modules.BaseTimeEntity;
import me.weekbelt.wetube.modules.member.form.ChangeEmailForm;
import me.weekbelt.wetube.modules.member.form.ChangePasswordForm;
import me.weekbelt.wetube.modules.member.form.MemberUpdateForm;

import javax.persistence.*;

@Getter @NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String profileImage;
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public Member(String name, String email, String password, String profileImage, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.role = role;
    }

    public void updateProfile(MemberUpdateForm memberUpdateForm) {
        this.name = memberUpdateForm.getName();
        // TODO: 사진 Url 업데이트
    }

    public void changeEmail(ChangeEmailForm changeEmailForm) {
        this.email = changeEmailForm.getEmail();
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
