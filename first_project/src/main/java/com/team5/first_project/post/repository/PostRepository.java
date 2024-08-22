package com.team5.first_project.post.repository;

import com.team5.first_project.board.entity.Board;
import com.team5.first_project.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    //Page<Post> findAll(Long id, Pageable pageable);
    Page<Post> findAllByBoard(Board board, Pageable pageable);
}
