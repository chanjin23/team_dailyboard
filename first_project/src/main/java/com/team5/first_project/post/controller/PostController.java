package com.team5.first_project.post.controller;

import com.team5.first_project.post.dto.PostResponseDto;
import com.team5.first_project.post.entity.Post;
import com.team5.first_project.post.service.PostService;
import com.team5.first_project.comment.entity.Comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.*;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    // 전체 게시글 조회
    @GetMapping("/posts")
    public String getAllPosts(Model model) {
        List<PostResponseDto> posts = postService.findAll()
                .stream()
                .map(PostResponseDto::new)
                .toList();

        model.addAttribute("posts", posts);

        return "board";
    }

    // 개별 게시글 조회
    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable("id") long id, Model model) {
        Post post = postService.findById(id);

        model.addAttribute("post", new PostResponseDto(post));
        model.addAttribute("comments", new ArrayList<>());

        // Comments (comment 원소) 리스트타입을 model.addAttribute();

        return "post";
    }

    // 게시글 삭제
    @DeleteMapping("post/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") long id) {
        postService.delete(id);
        return ResponseEntity.ok()
                .build();
    }
}
