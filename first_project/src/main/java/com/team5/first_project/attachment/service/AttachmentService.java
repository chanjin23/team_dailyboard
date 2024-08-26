package com.team5.first_project.attachment.service;

import com.team5.first_project.attachment.dto.AttachmentRequestDto;
import com.team5.first_project.attachment.entity.Attachment;
import com.team5.first_project.attachment.repository.AttachmentRepository;
import com.team5.first_project.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    public void saveFile(Post post, AttachmentRequestDto attachmentRequestDto){
        Attachment attachment = new Attachment(post, attachmentRequestDto);
        attachmentRepository.save(attachment);
    }
}
