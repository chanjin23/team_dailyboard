package com.team5.first_project.post.dto;

import com.team5.first_project.post.entity.Post;
import lombok.Getter;

/*
    RequestDto
    클라이언트가 서버로 데이터를 보낼 때, 특히 POST 또는 PUT 요청 시 필요한 데이터를 담음
    게시글을 생성하거나 수정할 때 사용
    게시글의 제목, 내용 등 클라이언트에서 서버로 전달해야 할 데이터만 포함
*/

@Getter
public class PostRequestDto {

    private String title;

    private String content;

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .build();
    }
}
