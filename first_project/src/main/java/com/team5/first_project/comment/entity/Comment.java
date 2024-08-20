package com.team5.first_project.comment.entity;

import com.team5.first_project.comment.dto.CommentPostDto;
import com.team5.first_project.comment.dto.CommentResponseDto;
import com.team5.first_project.member.entity.Member;
import com.team5.first_project.post.entity.Post;
import com.team5.first_project.timestamp.Timestamp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Comment(String content) {
        this.content = content;
    }

    public CommentResponseDto toCommentResponseDto() {
        return new CommentResponseDto(id,content);
    }
}
