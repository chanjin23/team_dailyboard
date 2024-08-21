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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 생성
    @GetMapping("/posts/create")
    public String createPage(@RequestParam("boardId") long boardId, Model model) {
        model.addAttribute("boardId", boardId);
        return "post/createPost";
    }

    // @AuthenticationPrincipal
    @PostMapping("/posts/create")
    public String createPost(@RequestParam("boardId") long id,
                             @Valid @ModelAttribute PostRequestDto postRequestDto,
                             BindingResult bindingResult){
        // 오류가 나면 다시 생성으로
        if(bindingResult.hasErrors()){
            return "post/createPost";
        }
        PostResponseDto postResponseDto = postService.createPost(id, postRequestDto);
        return "redirect:/boards" + id;
    }

//    // 전체 게시글 조회
//    @GetMapping("/boards/{boardId}")
//    public String getAllPosts(@PathVariable("boardId") long id, Model model) {
//        List<PostResponseDto> posts = postService.findAll()
//                .stream()
//                .map(PostResponseDto::new)
//                .toList();
//
//        model.addAttribute("posts", posts);
//
//        return "board/board";
//    }

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
    @GetMapping("/post/{postId}/edit")
    public String editPage(@PathVariable("postId") Long id, Model model){
        Post post = postService.findById(id);
        model.addAttribute("post", new PostResponseDto(post));
        return "post/editPost";
    }

    // @AuthenticationPrincipal
    @PostMapping("/post/{postId}/edit")
    public String updatePost(@PathVariable("postId") Long id,
                                       @Valid @ModelAttribute PostRequestDto requestDto){
        PostResponseDto postResponseDto = postService.updatePost(id, requestDto);
        return "redirect:boards" + postResponseDto.getBoardId();
    }

    // 게시글 삭제
    @PostMapping("posts/{postId}")
    public String deletePost(@PathVariable("postId") long id) {
        postService.delete(id);
        return "redirect:/posts";
    }

}
