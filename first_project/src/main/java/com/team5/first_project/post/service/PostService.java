package com.team5.first_project.post.service;

import com.team5.first_project.post.dto.PostRequestDto;
import com.team5.first_project.post.dto.PostResponseDto;
import com.team5.first_project.post.entity.Post;
import com.team5.first_project.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 게시글 생성
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto){
        Post post = new Post(postRequestDto);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    // 게시글 조회
    // 전체 게시글 조회
    @Transactional
    public List<Post> findAll() { return postRepository.findAll(); }

    // 개별 게시글 조회
    @Transactional
    public Post findById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }


    // 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글은 존재하지 않는 게시글입니다.")
        );
        post.update(requestDto);
        return new PostResponseDto(post);
    }

    // 게시글 삭제
    @Transactional
    public void delete(long id) {
        postRepository.deleteById(id);
    }

}
