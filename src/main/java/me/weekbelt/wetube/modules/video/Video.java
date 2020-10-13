package me.weekbelt.wetube.modules.video;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.wetube.modules.BaseTimeEntity;
import me.weekbelt.wetube.modules.member.Member;

import javax.persistence.*;

@Getter @NoArgsConstructor
@Entity
public class Video extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String title;

    @Lob
    private String description;

    private Long views;

    private String videoFile;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Video(String title, String description, Long views, String videoFile, Member member) {
        this.title = title;
        this.description = description;
        this.views = views;
        this.videoFile = videoFile;
        this.member = member;
    }
}
