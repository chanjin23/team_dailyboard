package com.elice.c4.post.controller;

import com.elice.c4.post.domain.Post;
import com.elice.c4.post.dto.PostDTO;
import com.elice.c4.post.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api") //localhost:8080/api
public class post_controller {
    private final PostService postService;

    public post_controller(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("/posts")
    public List<Post> getAllPosts(){
        return  postService.getAllPosts();
    }

    @PostMapping("/posts")
    public Post creatPost(@RequestBody PostDTO postDTO){
        return postService.savePost(postDTO);

    }
}
