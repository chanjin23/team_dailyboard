package com.team5.first_project.comment.service;

import com.team5.first_project.comment.dto.CommentPostDto;
import com.team5.first_project.comment.dto.CommentResponseDto;
import com.team5.first_project.comment.entity.Comment;
import com.team5.first_project.comment.repository.CommentRepository;
import com.team5.first_project.post.entity.Post;
import com.team5.first_project.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 댓글 생성
    @Transactional
    public void createComment(long id, CommentPostDto commentPostDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() ->new IllegalArgumentException("존재하지 않는 게시글입니다."));;
        Comment comment = new Comment(post, commentPostDto);
        commentRepository.save(comment);
    }

    // 댓글 전체 조회
    @Transactional
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    // 댓글 개별 조회
    @Transactional
    public Comment findComment(long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() ->new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id: " + id));
        return comment;
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(long id, CommentPostDto commentPostDto){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        comment.setContent(commentPostDto.getContent());
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }
}
