package com.team5.first_project.post.controller;

import com.team5.first_project.post.dto.PostRequestDto;
import com.team5.first_project.post.dto.PostResponseDto;
import com.team5.first_project.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 생성
    // @AuthenticationPrincipal
    @PostMapping("/post")
    public String createPost(@Valid @RequestBody PostRequestDto postRequestDto){
        PostResponseDto postResponseDto = postService.createPost(postRequestDto);
        return "board";
    }

    // 게시글 수정
    // @AuthenticationPrincipal
    @PatchMapping("/posts/{postId}")
    public String updatePost(@PathVariable Long postId,
                                       @Valid @RequestBody PostRequestDto requestDto){
        postService.updatePost(postId, requestDto);
        return "board";
    }
}
