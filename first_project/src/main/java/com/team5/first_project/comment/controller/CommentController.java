package com.team5.first_project.comment.controller;

import com.team5.first_project.comment.dto.CommentRequestDto;
import com.team5.first_project.comment.dto.CommentResponseDto;
import com.team5.first_project.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 조회
//    @GetMapping("/comments")
//    public String findAllComments(Model model) {
//        List<Comment> comments = commentService.findAllComments();
//        List<CommentResponseDto> commentResponseDto = comments.stream()
//                .map(comment -> new CommentResponseDto(comment))
//                .toList();
//
//        model.addAttribute("comments", commentResponseDto);
//        return "post/test";
//    }

    // 댓글 삭제
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build(); // 204 No Content 응답을 반환
    }

    // 댓글 생성
    @PostMapping("/comments")
    public String createComment(@RequestParam("postId") long id,
                                @Valid @ModelAttribute CommentRequestDto commentRequestDto) {
        commentService.createComment(id, commentRequestDto);
        return "redirect:/posts/" + id;
    }

    // 댓글 수정
    @PostMapping("/comments/{commentId}/edit")
    public String updateComment(@PathVariable("commentId") long id,
                                @Valid @ModelAttribute CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = commentService.updateComment(id, commentRequestDto);
        return "redirect:/posts/" + commentResponseDto.getPostId();
    }
}
