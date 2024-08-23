package com.team5.first_project.board.controller;

import com.team5.first_project.board.dto.RequestBoardDto;
import com.team5.first_project.board.dto.ResponseBoardDto;
import com.team5.first_project.board.entity.Board;
import com.team5.first_project.board.service.BoardService;
import com.team5.first_project.post.dto.PostResponseDto;
import com.team5.first_project.post.entity.Post;
import com.team5.first_project.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 특정 게시판 ID로 조회
    @GetMapping("/{boardId}")
    public String getBoardById(@PathVariable("boardId") Long id,
                               @RequestParam(value = "keyword", defaultValue = "") String keyword,
                               Pageable pageable,
                               Model model) {
        Board board = boardService.getBoardById(id);
        Page<Post> filterPosts = null;
        if (keyword.isEmpty()) {
            filterPosts = postService.findAll(board, pageable);
        } else {
            filterPosts = postService.findKeyword(board, keyword, pageable);
            model.addAttribute("keyword", keyword);
        }

        model.addAttribute("board", board);
        model.addAttribute("postPage", filterPosts);
        return "board/board";
    }

    // 특정 게시판 ID로 조회
//    @GetMapping("/{boardId}")
//    public String getBoardById(@PathVariable("boardId") Long id, Model model) {
//        Board board = boardService.getBoardById(id);
//        List<PostResponseDto> filterPosts = postService.findAll(id);
//
//        model.addAttribute("board", board);
//        model.addAttribute("postPage", filterPosts);
//        return "board/board";
//    }


    // 게시판 생성
    @GetMapping("/create")
    public String createBoard() {
        return "board/createBoard";
    }

    // 게시판 생성
    @PostMapping("/create")
    public String createBoard(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("type") String type,
                              Model model) {
        ResponseBoardDto saveBoard = boardService.saveBoard(name, description, type);
        model.addAttribute("boards", saveBoard);
        return "redirect:/boards";
    }

    // 게시판 수정 폼 조회
    @GetMapping("/{id}/edit")
    public String editBoard(@PathVariable("id") Long id, Model model) {
        Board board = boardService.getBoardById(id);
        model.addAttribute("board", board);
        return "board/editBoard";
    }

    //게시판 수정
    @PostMapping("/{id}/edit")
    public String editBoardPage(@PathVariable("id") Long id,
                                @RequestParam("description") String description,
                                @RequestParam("name") String name,
                                @RequestParam("type") String type) {
        boardService.updateBoard(id, description, name, type);
        return "redirect:/boards";
    }

}
