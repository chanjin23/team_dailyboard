package com.team5.first_project.comment.dto;

import com.team5.first_project.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentResponseDto {

    private String content;
    private long postId;

    public CommentResponseDto(Comment comment){
        this.content = comment.getContent();
        this.postId = comment.getPost().getId();
    }

}
