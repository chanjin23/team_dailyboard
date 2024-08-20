package com.team5.first_project.comment.dto;

import com.team5.first_project.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentPostDto {
    private String content;

    public Comment toEntity() {
        return new Comment(content);
    }
}
