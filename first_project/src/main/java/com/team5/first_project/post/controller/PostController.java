package com.team5.first_project.post.controller;

import com.team5.first_project.post.dto.PostRequestDto;
import com.team5.first_project.post.dto.PostResponseDto;
import com.team5.first_project.post.entity.Post;
import com.team5.first_project.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 생성
    @GetMapping("/posts/create/{id}")
    public String createPage(@PathVariable("id") long id, Model model){
        model.addAttribute("boardId", id);
        return "createPost";
    }

    // @AuthenticationPrincipal
    @PostMapping("/posts/create/{id}")
    public String createPost(@PathVariable("id") long id,
                             @Valid @ModelAttribute PostRequestDto postRequestDto){
        // postRequestDto.setBoardId(id);
        // requestDto에 boardId추가 후 주석 해제
        PostResponseDto postResponseDto = postService.createPost(postRequestDto);
        return "redirect:/board/";
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
    @GetMapping("/posts/{postId}")
    public String getPost(@PathVariable("postId") long id, Model model) {
        Post post = postService.findById(id);

        model.addAttribute("post", new PostResponseDto(post));
        //model.addAttribute("comments", new ArrayList<>());
        // Comments (comment 원소) 리스트타입을 model.addAttribute();

        return "post/post";
    }


    // 게시글 수정
    @GetMapping("/post/{id}/edit")
    public String editPage(@PathVariable("id") Long id, Model model){
        Post post = postService.findById(id);
        model.addAttribute("post", new PostResponseDto(post));
        return "editPost";
    }

    // @AuthenticationPrincipal
    @PostMapping("/post/{id}/edit")
    public String updatePost(@PathVariable("id") Long id,
                                       @Valid @ModelAttribute PostRequestDto requestDto){
        postService.updatePost(id, requestDto);
        return "redirect:/board/";
    }

    // 게시글 삭제
    @DeleteMapping("posts/{postId}")
    public String deletePost(@PathVariable("postId") long id) {
        postService.delete(id);
        return "redirect:/posts";
    }

}
