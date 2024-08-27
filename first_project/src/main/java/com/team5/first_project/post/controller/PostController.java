package com.team5.first_project.post.controller;

import com.team5.first_project.attachment.dto.AttachmentRequestDto;
import com.team5.first_project.attachment.service.AttachmentService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final BoardService boardService;
    private final AttachmentService attachmentService;

    // 게시글 생성
    @GetMapping("/create")
    public String createPage(@RequestParam("boardId") long boardId, Model model) {
        model.addAttribute("boardId", boardId);
        return "post/createPost";
    }

    @PostMapping("/create")
    public String createPost(@RequestParam("boardId") long id,
                             @Valid @ModelAttribute PostRequestDto postRequestDto,
                             BindingResult bindingResult,
                             @RequestParam(value = "file", required = false) MultipartFile file,
                             HttpSession session) {
//        if (bindingResult.hasErrors()) {
//            return "post/createPost";
//        }

        Member member = (Member) session.getAttribute("member");
        Post post = postService.createPost(id, postRequestDto, member);

        if (file != null && !file.isEmpty()) {
            try {
                // 고유한 파일 이름 생성 (파일 이름 충돌 방지를 위해 UUID 사용)
                String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get("path/to/upload/directory/" + fileName);

                // 파일 저장
                Files.write(filePath, file.getBytes());

                // 파일 접근 URL 생성 (파일 접근 경로에 맞게 수정)
                String fileDownloadUri = "/files/" + fileName;

                // 파일 정보를 데이터베이스에 저장 (필요에 따라 구현)
                AttachmentRequestDto attachmentRequestDto = new AttachmentRequestDto();
                attachmentRequestDto.setOriginFileName(fileName);
                attachmentRequestDto.setFilePath(fileDownloadUri);
                attachmentService.saveFile(post, attachmentRequestDto);

            } catch (IOException e) {
                e.printStackTrace();
                bindingResult.reject("fileUploadError", "파일 업로드에 실패했습니다.");
                return "post/createPost";
            }
        }

        return "redirect:/boards/" + id;
    }



    // 개별 게시글 조회
    @GetMapping("/{postId}")
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

    // 게시글 수정
    @GetMapping("/{postId}/edit")
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

    @PostMapping("/{postId}/edit")
    public String updatePost(@PathVariable("postId") Long id,
                             @Valid @ModelAttribute PostRequestDto requestDto) {
        PostResponseDto postResponseDto = postService.updatePost(id, requestDto);
        return "redirect:/posts/" + id;
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
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
