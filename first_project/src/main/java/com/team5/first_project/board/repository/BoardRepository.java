package com.team5.first_project.board.repository;

import com.team5.first_project.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByType(String type);
}
