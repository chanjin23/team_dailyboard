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
    private String filePath;
    private String fileName;

    public ResponseBoardDto(String name, String description, String type, String filePath, String fileName) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.filePath = filePath;
        this.fileName = fileName;
    }
}
