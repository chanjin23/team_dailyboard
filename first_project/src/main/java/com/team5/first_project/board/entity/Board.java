package com.team5.first_project.board.entity;

import com.team5.first_project.board.dto.RequestBoardDto;
import com.team5.first_project.board.dto.ResponseBoardDto;
import com.team5.first_project.post.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Board {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Column(nullable = false)
    private String type; // 게시판의 종류

    private String fileName;    //파일 이름

    private String filePath;    //파일이 저장된 경로

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> postList;


    public Board(RequestBoardDto requestBoardDto) {
        this.id = getId();
        this.name = requestBoardDto.getName();
        this.description = requestBoardDto.getDescription();
        this.type = requestBoardDto.getType();
        this.postList = getPostList();
    }

    public ResponseBoardDto toResponseBoardDto() {
        return new ResponseBoardDto(name, description, type, filePath, fileName);
    }

    public ResponseBoardDto toResponseUpdateBoardDto() {
        return new ResponseBoardDto(id, name, description, type, filePath, fileName);
    }

    public void update(String description, String name, String type) {
        this.description = description;
        this.name = name;
        this.type = type;
    }
}
