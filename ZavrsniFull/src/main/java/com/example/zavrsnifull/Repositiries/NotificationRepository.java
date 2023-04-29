package com.example.zavrsnifull.Repositiries;

import com.example.zavrsnifull.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query(value = "call get_notifications(:in_id);", nativeQuery = true)
    List<Notification> getByUser(@Param("in_id")Integer id);
}
