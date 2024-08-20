package com.team5.first_project.comment.controller;

import com.team5.first_project.comment.dto.CommentPostDto;
import com.team5.first_project.comment.dto.CommentResponseDto;
import com.team5.first_project.comment.entity.Comment;
import com.team5.first_project.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comments")
    public String findAllComments(Model model) {
        List<Comment> comments = commentService.findAllComments();
        List<CommentResponseDto> commentResponseDto = comments.stream()
                .map(comment -> new CommentResponseDto(comment.getId(), comment.getContent()))
                .toList();

        model.addAttribute("comments", commentResponseDto);
        return "post/test";
    }

    @PostMapping("/comments/{id}")
    public String deleteComment(@PathVariable("id") long id, Model model) {
        Comment comment = commentService.findComment(id);
        commentService.deleteComment(id);
        return "post/test";
    }

    @PostMapping("/comments")
    public String createComment(@RequestBody CommentPostDto commentPostDto, Model model) {
        Comment comment = commentPostDto.toEntity();
        Comment newComment = commentService.createComment(comment);

        CommentResponseDto commentResponseDto = newComment.toCommentResponseDto();
        model.addAttribute("comments", commentResponseDto);
        return "post/test";
    }
}
