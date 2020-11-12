package me.weekbelt.wetube.modules.FileInfo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.weekbelt.wetube.modules.BaseTimeEntity;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "file_info")
public class FileInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String saveFileName;

    @Column(nullable = false)
    private String contentType;

    @Builder
    public FileInfo(String fileName, String saveFileName, String contentType) {
        this.fileName = fileName;
        this.saveFileName = saveFileName;
        this.contentType = contentType;
    }
}
