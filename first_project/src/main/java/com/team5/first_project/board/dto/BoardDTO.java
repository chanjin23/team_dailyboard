package com.team5.first_project.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoardDTO {
    private Long id;            // 게시판의 고유 ID
    private String name;       // 게시판 이름
    private String description; // 게시판 설명
    private String type;
}
