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

    public Media() {}

    public Media(Post post, MediaType mediaType, String fileName, String filePath) {
        this.post = post;
        this.mediaType = mediaType;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Media{" +
                "post=" + post +
                ", mediaType=" + mediaType +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
