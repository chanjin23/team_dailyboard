package com.team5.first_project.board.controller;

import com.team5.first_project.board.dto.BoardDTO;
import com.team5.first_project.board.dto.RequestBoardDto;
import com.team5.first_project.board.dto.ResponseBoardDto;
import com.team5.first_project.board.entity.Board;
import com.team5.first_project.board.service.BoardService;
import com.team5.first_project.post.dto.PostResponseDto;
import com.team5.first_project.post.service.PostService;
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
    private final PostService postService;

    // 모든 게시판 조회
    @GetMapping
    public String getAllBoards(Model model) {
        List<Board> boards = boardService.getAllBoards();
        model.addAttribute("boards", boards);
        return "board/boards";
    }

    // 게시판 ID로 조회
    @GetMapping("/{boardId}")
    public String getBoardById(@PathVariable("boardId") Long id, Model model) {
        Board board = boardService.getBoardById(id);
        List<PostResponseDto> filterPosts = postService.findAll()
                .stream()
                .filter((p) -> p.getBoardId().equals(id))
                .toList();

        model.addAttribute("board", board);
        model.addAttribute("postPage", filterPosts);
        return "board/board";
    }

    // 게시판 생성
    @GetMapping("/create")
    public String createBoard() {
        return "board/createBoard";
    }

    @PostMapping("/create")
    public String createBoard(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("type") String type,
                              Model model) {
        ResponseBoardDto saveBoard = boardService.saveBoard(name, description, type);
        model.addAttribute("boards", saveBoard);
        return "redirect:/boards";
    }

    // 게시판 수정
    @GetMapping("/{id}/edit")
    public String editBoard(@PathVariable("id") Long id, Model model) {
        Board board = boardService.getBoardById(id);
        model.addAttribute("board", board);
        return "board/editBoard";
    }

    @PostMapping("/{id}/edit")
    public String editBoardPage(@PathVariable("id") Long id,
                                @RequestParam("description") String description,
                                @RequestParam("name") String name,
                                @RequestParam("type") String type) {
        boardService.updateBoard(id, description, name, type);
        return "redirect:/boards";
    }

    // 게시판 타입으로 조회

    @GetMapping("/type/{type}")
    public ResponseEntity<List<BoardDTO>> getBoardsByType(@PathVariable String type) {
        List<BoardDTO> boards = boardService.getBoardsByType(type);
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }
}
