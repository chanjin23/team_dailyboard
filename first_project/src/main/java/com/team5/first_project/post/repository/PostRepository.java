package com.team5.first_project.post.repository;

import com.team5.first_project.board.entity.Board;
import com.team5.first_project.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    //Page<Post> findAll(Long id, Pageable pageable);
    Page<Post> findAllByBoard(Board board, Pageable pageable);

    Page<Post> findByBoardAndTitleContainingOrBoardAndContentContaining(Board board1, String keyword1, Board board2, String keyword2, Pageable pageable);

    //@Query("SELECT p FROM Post p WHERE p.board.id = :boardId AND (p.title LIKE %:keyword1% OR p.content LIKE %:keyword2%)")
    //Page<Post> findByBoardIdAndTitleContainingOrContentContaining(@Param("boardId") Long boardId, String keyword1, String keyword2, Pageable pageable);
}
