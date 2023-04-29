package com.example.zavrsnifull.Repositiries;

import com.example.zavrsnifull.entities.Follow;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.config.SpringDataJacksonConfiguration;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {

    @Query(value = "call get_follow_data('followee', :in_id)", nativeQuery = true)
    List<Follow> getFollowers(@Param("in_id") Integer id);

    @Query(value = "call get_follow_data('follower', :in_id)", nativeQuery = true)
    List<Follow> getFollowees(@Param("in_id") Integer id);

    @Query(value = "call get_follow(:in_followerid, :in_followeeid)", nativeQuery = true)
    Optional<Follow> get(@Param("in_followerid") Integer followerid, @Param("in_followeeid") Integer followeeid);
}
