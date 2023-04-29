package com.example.zavrsnifull.Repositiries;

import com.example.zavrsnifull.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "call check_image_name('users',:in_name);", nativeQuery = true)
    Optional<User> getImageByName(@Param("in_name") String name);

    @Query(value = "call check_user(:in_username);",nativeQuery = true)
    Optional<User> getUserByUsername(@Param("in_username") String username);

    @Query(value = "call search_users(:in_username);", nativeQuery = true)
    List<User> search(@Param("in_username") String username);

    @Query(value = "call log_in(:in_username,:in_password);", nativeQuery = true)
    Optional<User> logIn(@Param("in_username") String username,@Param("in_password") String password);
}