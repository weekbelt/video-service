package me.weekbelt.wetube.modules.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.wetube.modules.BaseTimeEntity;
import me.weekbelt.wetube.modules.member.Member;
import me.weekbelt.wetube.modules.video.Video;

import javax.persistence.*;

@Getter @NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void addVideo(Video video) {
        this.video = video;
        video.getComments().add(this);
    }
}
