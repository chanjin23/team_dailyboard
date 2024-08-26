package com.team5.first_project.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseBoardDto {
    private Long id;
    private String name;
    private String description;
    private String type;

    public ResponseBoardDto(String name, String description,String type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }
}
