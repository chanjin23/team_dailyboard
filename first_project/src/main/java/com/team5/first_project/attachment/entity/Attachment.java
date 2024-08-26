package com.team5.first_project.attachment.entity;

import com.team5.first_project.attachment.dto.AttachmentRequestDto;
import com.team5.first_project.post.dto.PostResponseDto;
import com.team5.first_project.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;

    private String fileType;

    private String originFileName;

    private Long fileSize;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Attachment(Post post, AttachmentRequestDto attachmentRequestDto){
        this.filePath = attachmentRequestDto.getFilePath();
//        this.fileType = attachmentRequestDto.getFileType();
        this.originFileName = attachmentRequestDto.getOriginFileName();
//        this.fileSize = attachmentRequestDto.getFileSize();
        this.post = post;
    }
}
