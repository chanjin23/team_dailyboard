package com.team5.first_project.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/*
    RequestDto
    클라이언트가 서버로 데이터를 보낼 때, 특히 POST 또는 PUT 요청 시 필요한 데이터를 담음
    게시글을 생성하거나 수정할 때 사용
    게시글의 제목, 내용 등 클라이언트에서 서버로 전달해야 할 데이터만 포함
*/

@Getter
public class PostRequestDto {

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 30, message = "제목은 최대 30자까지만 허용됩니다.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 2500, message = "내용은 2500자까지만 허용됩니다.")
    private String content;

}
