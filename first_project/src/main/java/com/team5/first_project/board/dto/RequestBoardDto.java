package com.team5.first_project.board.dto;

import com.team5.first_project.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestBoardDto {
    private Long id;
    private String name;
    private String description;
    private String type;

    public RequestBoardDto(String name, String description, String type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public Board toEntity(RequestBoardDto requestBoardDto) {
        return new Board(requestBoardDto);
    }
}
