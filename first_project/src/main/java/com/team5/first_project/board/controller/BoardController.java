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
        List<BoardDTO> boards = boardService.getAllBoards();
        model.addAttribute("boards", boards);
        return "board/boards";
    }

    // 게시판 ID로 조회
    @GetMapping("/{boardId}")
    public String getBoardById(@PathVariable("boardId") Long id, Model model) {
        BoardDTO board = boardService.getBoardById(id);
        model.addAttribute("board", board);

        List<PostResponseDto> posts = postService.findAll()
                .stream()
                .map(PostResponseDto::new)
                .toList();

        model.addAttribute("postPage", posts);
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
        RequestBoardDto requestBoardDto = new RequestBoardDto(name, description, type);
        Board board = new Board(requestBoardDto);
        Board saveBoard = boardService.saveBoard(board);

        ResponseBoardDto responseBoardDto = saveBoard.toResponseBoardDto();
        System.out.println("BoardController.createBoard");
        model.addAttribute("boards", responseBoardDto);
        return "redirect:/boards";
    }

    // 게시판 수정
    @PutMapping("/{id}/edit")
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
