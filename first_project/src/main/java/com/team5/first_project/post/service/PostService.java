package com.team5.first_project.post.service;

import com.team5.first_project.board.repository.BoardRepository;
import com.team5.first_project.exception.NotFoundByBoardIdException;
import com.team5.first_project.exception.NotFoundByPostIdException;
import com.team5.first_project.member.entity.Member;
import com.team5.first_project.post.dto.PostRequestDto;
import com.team5.first_project.post.dto.PostResponseDto;
import com.team5.first_project.post.entity.Post;
import com.team5.first_project.board.entity.Board;
import com.team5.first_project.post.repository.PostRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    private static final int PAGE_SIZE = 10;

    // 게시글 생성
    @Transactional
    public Post createPost(long id, PostRequestDto postRequestDto, Member member){
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundByBoardIdException(id));
        Post post = new Post(board, postRequestDto, member);
        postRepository.save(post);
        return post;
    }

    // 게시글 조회
    // 전체 게시글 조회
    @Transactional
    public Page<Post> findAll(Board board, Pageable pageable) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdTime"));
        pageable = PageRequest.of(pageable.getPageNumber(), PAGE_SIZE, Sort.by(sorts));

        return postRepository.findAllByBoard(board, pageable);
    }

    // 키워드 조회
    @Transactional
    public Page<Post> findKeyword(Board board, String keyword, Pageable pageable) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdTime"));
        pageable = PageRequest.of(pageable.getPageNumber(), PAGE_SIZE, Sort.by(sorts));

        return postRepository.findByBoardAndTitleContainingOrBoardAndContentContaining(board, keyword, board, keyword, pageable);
    }

    // 개별 게시글 조회
    @Transactional
    public Post findById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundByPostIdException(id));
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundByPostIdException(id));
        post.update(requestDto);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    // 게시글 삭제
    @Transactional
    public void delete(long id) {
        postRepository.deleteById(id);
    }

    // 게시글의 작성자가 맞는지 확인
    public boolean postAuthorVerification(long id, HttpSession session){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundByPostIdException(id));
        Member member = (Member) session.getAttribute("member");
        if (member == null || post.getMember() == null) {
            return false;
        }
        if (post.getMember().getId() == member.getId()){
            return true;
        } else {
            return false;
        }
    }

    // 조회수 증가
    // 쿠키로 조회수 중복 불가 설정
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

    // 쿠키 설정
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

}
