package com.example.zavrsnifull.Repositiries;

import com.example.zavrsnifull.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

    @Query(value = "call count_likes(:in_userid);", nativeQuery = true)
    List<Like> getCount(@Param("in_userid") Integer id);

    @Query(value = "call remove_like(:in_postid, :in_userid)", nativeQuery = true)
    String remove(@Param("in_postid") Integer postId, @Param("in_userid") Integer userId);
}
