package sir.timekeep.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "MEMO_Media")
public class Media extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type", nullable = false)
    private MediaType mediaType;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "upload_time")
    private LocalDateTime uploadTime;

    @Column(name = "description")
    private String description;

    public Media() {}

    public Media(Post post, MediaType mediaType, String fileName, String filePath, Long fileSize, LocalDateTime uploadTime, String description) {
        this.post = post;
        this.mediaType = mediaType;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.uploadTime = uploadTime;
        this.description = description;
    }


}