package me.weekbelt.wetube.modules.video;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.wetube.modules.BaseTimeEntity;
import me.weekbelt.wetube.modules.comment.Comment;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.video.form.VideoUpdateForm;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @NoArgsConstructor
@Entity
public class Video extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String title;

    @Lob
    private String description;

    private Long views;

    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Video(String title, String description, Long views, String fileUrl, Member member) {
        this.title = title;
        this.description = description;
        this.views = views;
        this.fileUrl = fileUrl;
        this.member = member;
    }

    public void update(VideoUpdateForm videoUpdateForm) {
        this.title = videoUpdateForm.getTitle();
        this.description = videoUpdateForm.getDescription();
    }
}
