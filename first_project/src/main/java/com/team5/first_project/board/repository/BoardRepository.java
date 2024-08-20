package com.team5.first_project.board.repository;

import com.team5.first_project.board.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    List<Board> findAll();

    Optional<Board> findById(Long id);

    Board save(Board board);

    void deleteById(Long id);

    List<Board> findByType(String type);
}
