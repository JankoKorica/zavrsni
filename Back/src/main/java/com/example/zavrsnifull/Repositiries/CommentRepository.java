package com.example.zavrsnifull.Repositiries;

import com.example.zavrsnifull.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(value = "call get_comments(:in_postid)", nativeQuery = true)
    List<Comment> getAllByPost(@Param("in_postid") Integer id);
}
