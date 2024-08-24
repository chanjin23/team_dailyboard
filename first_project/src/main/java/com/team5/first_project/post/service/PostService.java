package com.team5.first_project.post.service;

import com.team5.first_project.board.repository.BoardRepository;
import com.team5.first_project.member.entity.Member;
import com.team5.first_project.post.dto.PostRequestDto;
import com.team5.first_project.post.dto.PostResponseDto;
import com.team5.first_project.post.entity.Post;
import com.team5.first_project.board.entity.Board;
import com.team5.first_project.post.repository.PostRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final static String VIEWCOOKIENAME = "alreadyViewCookie";

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

    @Transactional
    public int updateView(Long id, HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        boolean checkCookie = false;
        int result = 0;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(VIEWCOOKIENAME+id))
                    checkCookie = true;
            }
            if(!checkCookie){
                Cookie newCookie = createCookieForForNotOverlap(id);
                response.addCookie(newCookie);
                result = postRepository.updateView(id);
            }
        }
        else {
            Cookie newCookie = createCookieForForNotOverlap(id);
            response.addCookie(newCookie);
            result = postRepository.updateView(id);
        }
        return result;
    }

    private Cookie createCookieForForNotOverlap(Long id) {
        Cookie cookie = new Cookie(VIEWCOOKIENAME+id, String.valueOf(id));
        cookie.setMaxAge(getRemainSecondForTommorow()); 	// 하루를 준다.
        cookie.setHttpOnly(true);				// 서버에서만 조작 가능
        return cookie;
    }

    private int getRemainSecondForTommorow() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tommorow = LocalDateTime.now().plusDays(1L).truncatedTo(ChronoUnit.DAYS);
        return (int) now.until(tommorow, ChronoUnit.SECONDS);
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
