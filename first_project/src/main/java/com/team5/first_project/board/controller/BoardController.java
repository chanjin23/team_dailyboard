package com.team5.first_project.board.controller;

import com.team5.first_project.board.dto.BoardDTO;
import com.team5.first_project.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    // 모든 게시판 조회
    @GetMapping
    public String getAllBoards(Model model) {
        List<BoardDTO> boards = boardService.getAllBoards();
        model.addAttribute("boards", boards);
        return "board/boards";
    }

    // 게시판 ID로 조회
    @GetMapping("/{id}")
    public String getBoardById(@PathVariable Long id) {
        BoardDTO board = boardService.getBoardById(id);
        return "board/board";
    }

    // 게시판 생성
    @PostMapping
    public ResponseEntity<BoardDTO> createBoard(@RequestBody BoardDTO boardDTO) {
        BoardDTO BoardDTO = null;
        BoardDTO createdBoard = boardService.saveBoard(null);
        return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
    }

    // 게시판 수정
    @PutMapping("/{id}")
    public ResponseEntity<BoardDTO> updateBoard(@PathVariable Long id, @RequestBody BoardDTO boardDTO) {
        BoardDTO BoardDTO = null;
        BoardDTO updatedBoard = boardService.updateBoard(id, null);
        return new ResponseEntity<>(updatedBoard, HttpStatus.OK);
    }

    // 게시판 삭제
    @PostMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 게시판 타입으로 조회

    @GetMapping("/type/{type}")
    public ResponseEntity<List<BoardDTO>> getBoardsByType(@PathVariable String type) {
        List<BoardDTO> boards = boardService.getBoardsByType(type);
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }
}
