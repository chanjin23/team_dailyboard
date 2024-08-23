package com.team5.first_project.post.service;

import com.team5.first_project.board.repository.BoardRepository;
import com.team5.first_project.member.entity.Member;
import com.team5.first_project.post.dto.PostRequestDto;
import com.team5.first_project.post.dto.PostResponseDto;
import com.team5.first_project.post.entity.Post;
import com.team5.first_project.board.entity.Board;
import com.team5.first_project.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    // 게시글 생성
    @Transactional
    public PostResponseDto createPost(long id, PostRequestDto postRequestDto, Member member){
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));
        Post post = new Post(board, postRequestDto, member);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    // 키워드 조회
    @Transactional
    public Page<Post> findKeyword(Board board, String keyword, Pageable pageable) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdTime"));
        pageable = PageRequest.of(pageable.getPageNumber(), 10, Sort.by(sorts));

        return postRepository.findByBoardAndTitleContainingOrBoardAndContentContaining(board, keyword, board, keyword, pageable);
    }

    // 게시글 조회
    // 전체 게시글 조회
    @Transactional
    public Page<Post> findAll(Board board, Pageable pageable) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdTime"));
        pageable = PageRequest.of(pageable.getPageNumber(), 10, Sort.by(sorts));

        return postRepository.findAllByBoard(board, pageable);
    }


    // 게시글 조회
//    // 전체 게시글 조회
//    @Transactional
//    public List<PostResponseDto> findAll(Long boardId) {
//        return postRepository.findAll()
//                .stream()
//                .filter((p)->p.getBoard().getId().equals(boardId))
//                .map(PostResponseDto::new)
//                .toList();
//    }


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
        postRepository.save(post);
        return new PostResponseDto(post);
    }


    // 게시글 삭제
    @Transactional
    public void delete(long id) {
        postRepository.deleteById(id);
    }

}
