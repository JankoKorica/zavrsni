package com.example.zavrsnifull.services;

import com.example.zavrsnifull.Repositiries.NotificationRepository;
import com.example.zavrsnifull.Repositiries.PostRepository;
import com.example.zavrsnifull.entities.Notification;
import com.example.zavrsnifull.entities.Post;
import com.example.zavrsnifull.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PostRepository postRepository;

    public List<Notification> getByUser(Integer id){
        return notificationRepository.getByUser(id);
    }

    public Notification createNew(Notification notification){
        return notificationRepository.save(notification);
    }

    public Notification saveByPostId(LocalDateTime time, String content, Integer postid){

        Post post = postRepository.findById(postid).orElse(null);

        if (post == null)
            return null;

        return notificationRepository.save(new Notification(
                null,
                LocalDateTime.now(),
                content,
                false,
                post.getUser()
        ));
    }

    @Transactional
    public void setVeiwed(Integer id){

        List<Notification> notifications = notificationRepository.getByUser(id);

        for (Notification notification : notifications){
            notification.setViewed(true);
        }
    }
}
