package com.team5.first_project.post.dto;

import com.team5.first_project.comment.entity.Comment;
import com.team5.first_project.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
    PostResponseDto
    서버가 클라이언트에게 데이터를 보낼 때, 즉 GET 요청에 대한 응답으로 사용
    게시글 생성 또는 수정 후 반환할 데이터나, 단일 게시글 조회 시 주로 사용
    게시글 ID, 제목, 내용, 작성자 정보, 작성/수정 시간 등 클라이언트가 필요로 하는 데이터를 포함
*/

@Getter
@NoArgsConstructor
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    // private String memberName;
    private String boardName;
    private LocalDateTime recentTime;
    private List<Comment> comments;
    private Long boardId;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        // this.memberName = post.getMember().getName();
        this.boardName = post.getBoard().getName();
        this.recentTime = post.getUpdatedTime() == null ? post.getCreatedTime() : post.getUpdatedTime();
        this.comments = post.getCommentList();
        this.boardId = post.getBoard().getId();
    }

}
