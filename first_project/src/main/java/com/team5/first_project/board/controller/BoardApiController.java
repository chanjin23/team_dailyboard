package com.team5.first_project.board.controller;

import com.team5.first_project.board.entity.Board;
import com.team5.first_project.board.service.BoardService;
import com.team5.first_project.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("boards")
@RequiredArgsConstructor
public class BoardApiController {
    private final BoardService boardService;
    private final PostService postService;

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("boardId") Long boardId) {
        boardService.deleteBoard(boardId);
        System.out.println("BoardController.deleteBoard");
//        return ResponseEntity.ok()
//                .build();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
