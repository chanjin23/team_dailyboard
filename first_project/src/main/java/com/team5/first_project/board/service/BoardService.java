package com.team5.first_project.board.service;

import com.team5.first_project.board.dto.RequestBoardDto;
import com.team5.first_project.board.dto.ResponseBoardDto;
import com.team5.first_project.board.entity.Board;
import com.team5.first_project.board.repository.BoardRepository;
import com.team5.first_project.member.entity.Member;
import com.team5.first_project.member.entity.MemberRoleEnum;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * 모든 게시판 조회
     */
    @Transactional
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    /**
     * 특정 게시판 조회
     */
    @Transactional
    public Board getBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 게시판은 존재하지 않는 게시판:" + id
                ));
    }

    /**
     * 게시판 생성 (Create)
     */
    @Transactional
    public ResponseBoardDto saveBoard(String name, String description, String type) {
        RequestBoardDto requestBoardDto = new RequestBoardDto(name, description, type);
        Board board = requestBoardDto.toEntity(requestBoardDto);

        Board saveBoard = boardRepository.save(board);

        return saveBoard.toResponseBoardDto();
    }

    /**
     * 게시판 삭제
     */
    @Transactional
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("d"));

        boardRepository.deleteById(board.getId());
    }

    /**
     * 게시판 수정
     */
    @Transactional
    public void updateBoard(Long id, String description, String name, String type) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 게시판은 존재하지 않는 게시판:" + id
                ));
        board.update(description, name, type);

        boardRepository.save(board);
    }

    // 관리자인지 검증
    public boolean administratorVerification(HttpSession session){
        Member member = (Member) session.getAttribute("member");
        if (member == null){
            return false;
        }
        if (member.getRole() == MemberRoleEnum.ADMIN){
            return true;
        } else {
            return false;
        }
    }
}
