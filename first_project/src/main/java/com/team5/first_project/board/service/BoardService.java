package com.team5.first_project.board.service;

import com.team5.first_project.board.dto.BoardDTO;
import com.team5.first_project.board.dto.RequestBoardDto;
import com.team5.first_project.board.dto.ResponseBoardDto;
import com.team5.first_project.board.entity.Board;
import com.team5.first_project.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * 게시판 저장 (Create)
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
     * 게시판 타입으로 조회
     */
    @Transactional
    public List<BoardDTO> getBoardsByType(String type) {
        return boardRepository.findByType(type).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 엔티티를 DTO로 변환
    private BoardDTO convertToDTO(Board board) {
        return new BoardDTO(board.getId(), board.getName(), board.getDescription(), board.getType());
    }

    // DTO를 엔티티로 변환
    private Board convertToEntity(BoardDTO boardDTO) {
        return new Board(boardDTO.getId(), boardDTO.getName(), boardDTO.getDescription(), boardDTO.getType(), null);
    }

    @Transactional
    public Board findById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 게시판은 존재하지 않는 게시판:" + id
                ));
    }

    @Transactional
    public void updateBoard(Long id, String description, String name, String type) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 게시판은 존재하지 않는 게시판:" + id
                ));
        board.update(description, name, type);

        boardRepository.save(board);
    }
}
