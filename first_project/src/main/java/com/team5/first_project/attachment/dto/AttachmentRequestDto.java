package com.team5.first_project.attachment.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttachmentRequestDto {

    private String filePath;

//    private String fileType;

    private String originFileName;

//    private Long fileSize;
}
