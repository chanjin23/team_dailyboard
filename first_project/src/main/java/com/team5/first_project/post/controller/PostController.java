package com.team5.first_project.post.controller;

import com.team5.first_project.post.dto.PostRequestDto;
import com.team5.first_project.post.dto.PostResponseDto;
import com.team5.first_project.post.entity.Post;
import com.team5.first_project.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 생성
    // @AuthenticationPrincipal
    @PostMapping("/posts/create/{boardId}")
    public String createPost(@Valid @RequestBody PostRequestDto postRequestDto){
        PostResponseDto postResponseDto = postService.createPost(postRequestDto);
        return "redirect:/board/" + postResponseDto.getBoardId();
    }

    // 전체 게시글 조회
    @GetMapping("/posts")
    public String getAllPosts(Model model) {
        List<PostResponseDto> posts = postService.findAll()
                .stream()
                .map(PostResponseDto::new)
                .toList();

        model.addAttribute("posts", posts);

        return "board/board";
    }

    // 개별 게시글 조회
    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable("id") long id, Model model) {
        Post post = postService.findById(id);

        model.addAttribute("post", new PostResponseDto(post));
        //model.addAttribute("comments", new ArrayList<>());
        // Comments (comment 원소) 리스트타입을 model.addAttribute();

        return "post/test";
    }


    // 게시글 수정
    // @AuthenticationPrincipal
    @PatchMapping("/posts/{postId}")
    public String updatePost(@PathVariable Long postId,
                                       @Valid @RequestBody PostRequestDto requestDto){
        PostResponseDto postResponseDto = postService.updatePost(postId, requestDto);
        return "redirect:/board/" + postResponseDto.getBoardId();
    }

    // 게시글 삭제
    @DeleteMapping("posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") long id) {
        postService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

}
