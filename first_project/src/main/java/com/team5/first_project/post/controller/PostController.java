package com.team5.first_project.post.controller;

import com.team5.first_project.board.service.BoardService;
import com.team5.first_project.comment.entity.Comment;
import com.team5.first_project.comment.service.CommentService;
import com.team5.first_project.member.entity.Member;
import com.team5.first_project.post.dto.PostRequestDto;
import com.team5.first_project.post.dto.PostResponseDto;
import com.team5.first_project.post.entity.Post;
import com.team5.first_project.post.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final BoardService boardService;

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
                             BindingResult bindingResult,
                             HttpSession session) {
        // 오류가 나면 다시 생성으로
        if (bindingResult.hasErrors()) {
            return "post/createPost";
        }
        Member member = (Member) session.getAttribute("member");
        PostResponseDto postResponseDto = postService.createPost(id, postRequestDto, member);
        return "redirect:/boards/" + id;
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
    public String getPost(@PathVariable("postId") long id,
                          Model model,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        postService.updateView(id, request, response);
        Post post = postService.findById(id);
        List<Comment> comments = new PostResponseDto(post).getComments();
        List<Comment> orderComments = commentService.orderComment(comments);

        model.addAttribute("post", new PostResponseDto(post));
        model.addAttribute("comments", orderComments);
        model.addAttribute("member", post.getMember());
        // Comments (comment 원소) 리스트타입을 model.addAttribute();

        return "post/post";
    }
    /*

    // 특정 게시판 ID로 조회
    @GetMapping("/{boardId}")
    public String getBoardById(@PathVariable("boardId") Long id,
                               Pageable pageable,
                               Model model) {
        Board board = boardService.getBoardById(id);
        Page<Post> filterPosts = postService.findAll(board, pageable);

        model.addAttribute("board", board);
        model.addAttribute("postPage", filterPosts);
        return "board/board";
    }
    */


    // 게시글 수정
    @GetMapping("/posts/{postId}/edit")
    public String editPage(@PathVariable("postId") Long id, Model model,
                           HttpSession session) {
        if (postService.postAuthorVerification(id, session)){
            Post post = postService.findById(id);
            model.addAttribute("post", new PostResponseDto(post));
            return "post/editPost";
        } else {
            return "redirect:/posts/" +id;
        }
    }

    // @AuthenticationPrincipal
    @PostMapping("/posts/{postId}/edit")
    public String updatePost(@PathVariable("postId") Long id,
                             @Valid @ModelAttribute PostRequestDto requestDto) {
        PostResponseDto postResponseDto = postService.updatePost(id, requestDto);
        return "redirect:/posts/" + id;
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") long id,
                                           HttpSession session) {
        if (postService.postAuthorVerification(id, session) || boardService.administratorVerification(session)){
            postService.delete(id);
            return ResponseEntity.noContent().build(); // 204 No Content 응답을 반환
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}
