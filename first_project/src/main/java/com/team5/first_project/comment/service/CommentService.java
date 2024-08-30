package com.team5.first_project.comment.service;

import com.team5.first_project.comment.dto.CommentRequestDto;
import com.team5.first_project.comment.dto.CommentResponseDto;
import com.team5.first_project.comment.entity.Comment;
import com.team5.first_project.comment.repository.CommentRepository;
import com.team5.first_project.exception.NotFoundByCommentIdException;
import com.team5.first_project.exception.NotFoundByPostIdException;
import com.team5.first_project.member.entity.Member;
import com.team5.first_project.post.entity.Post;
import com.team5.first_project.post.repository.PostRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 댓글 생성
    @Transactional
    public void createComment(long id, CommentRequestDto commentRequestDto, Member member) {
        Post post = findPostById(id);
        Comment comment = new Comment(post, commentRequestDto, member);
        commentRepository.save(comment);
    }

    // 댓글 개별 조회
    @Transactional
    public CommentResponseDto findComment(long id) {
        Comment comment = findCommentById(id);
        return new CommentResponseDto(comment);
    }

    // 댓글 작성자인지 확인
    public boolean commentAuthorVerification(long id, HttpSession session){
        Comment comment = findCommentById(id);
        Member member = (Member) session.getAttribute("member");
        if (member == null || comment.getMember() == null) {
            return false;
        }
        if (comment.getMember().getId() == member.getId()){
            return true;
        } else {
            return false;
        }
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(long id, CommentRequestDto commentRequestDto){
        Comment comment = findCommentById(id);
        comment.setContent(commentRequestDto.getContent());
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }


    public List<Comment> orderComment(List<Comment> comments) {
        comments.sort(Comparator.comparing(Comment::getCreatedTime).reversed());

        return comments;
    }

    private Post findPostById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundByPostIdException(id));
    }

    private Comment findCommentById(long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundByCommentIdException(id));
    }
}
