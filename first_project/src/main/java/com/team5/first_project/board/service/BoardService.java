package com.team5.first_project.board.service;

import com.team5.first_project.board.dto.BoardDTO;
import com.team5.first_project.board.entity.Board;
import com.team5.first_project.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardService {

    @Autowired
    private static BoardRepository boardRepository;

    // 모든 게시판 조회
    public List<BoardDTO> getAllBoards() {
        return boardRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 게시판 조회
    public BoardDTO getBoardById(Long id) {
        return boardRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("게시판이 존재하지 않습니다."));
    }

    // 게시판 저장 (Create)
    public BoardDTO saveBoard(BoardDTO boardDTO) {
        Board board = convertToEntity(boardDTO);
        Board savedBoard = boardRepository.save(board);
        return convertToDTO(savedBoard);
    }

    // 게시판 수정 (Update)
    public BoardDTO updateBoard(Long id, BoardDTO boardDTO) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            board.setName(boardDTO.getName());
            board.setDescription(boardDTO.getDescription());
            board.setType(boardDTO.getType());
            Board updatedBoard = boardRepository.save(board);
            return convertToDTO(updatedBoard);
        } else {
            throw new RuntimeException("게시판이 존재하지 않습니다.");
        }
    }

    // 게시판 삭제
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    // 게시판 타입으로 조회
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
}
