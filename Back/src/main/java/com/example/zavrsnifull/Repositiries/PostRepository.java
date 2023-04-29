package com.example.zavrsnifull.Repositiries;

import com.example.zavrsnifull.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "call check_image_name('posts',:in_name);", nativeQuery = true)
    Optional<Post> getImageByName(@Param("in_name") String name);

    @Query(value = "call get_post(:in_what, :in_id, :in_offset);", nativeQuery = true)
    List<Post> getBy(@Param("in_what") String what, @Param("in_id") Integer id, @Param("in_offset") Integer offset);
}
