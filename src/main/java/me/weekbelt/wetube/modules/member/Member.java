package me.weekbelt.wetube.modules.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.wetube.modules.BaseTimeEntity;
import me.weekbelt.wetube.modules.FileInfo.FileInfo;
import me.weekbelt.wetube.modules.comment.Comment;
import me.weekbelt.wetube.modules.member.form.ChangeEmailForm;
import me.weekbelt.wetube.modules.member.form.ChangeNameForm;
import me.weekbelt.wetube.modules.member.form.MemberUpdateForm;
import me.weekbelt.wetube.modules.video.Video;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne
    @JoinColumn(name = "file_id")
    private FileInfo profileImage;

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Video> videos = new ArrayList<>();

    @Builder
    public Member(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void updateProfile(FileInfo profileImage) {
        this.profileImage = profileImage;
    }

    public void changeEmail(ChangeEmailForm changeEmailForm) {
        this.email = changeEmailForm.getEmail();
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void changeName(ChangeNameForm changeNameForm) {
        this.name = changeNameForm.getName();
    }
}
