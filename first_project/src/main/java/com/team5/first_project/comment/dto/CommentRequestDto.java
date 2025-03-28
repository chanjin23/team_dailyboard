package com.team5.first_project.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    @NotBlank(message = "댓글을 입력해주세요.")
    @Size(max = 100, message = "댓글은 100자 이하만 가능합니다.")
    private String content;
}
