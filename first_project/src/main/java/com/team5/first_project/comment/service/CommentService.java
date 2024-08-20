package com.team5.first_project.comment.service;

import com.team5.first_project.comment.entity.Comment;
import com.team5.first_project.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }

    public Comment findComment(long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() ->new IllegalArgumentException("해당 댓글이 존재하지 않습니다. id: " + id));
        return comment;
    }

    public void deleteComment(long id) {
        Comment comment = findComment(id);
        commentRepository.delete(comment);
    }
}
