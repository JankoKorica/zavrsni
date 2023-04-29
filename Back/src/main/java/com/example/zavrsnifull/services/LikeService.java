package com.example.zavrsnifull.services;

import com.example.zavrsnifull.Repositiries.LikeRepository;
import com.example.zavrsnifull.Repositiries.NotificationRepository;
import com.example.zavrsnifull.Repositiries.PostRepository;
import com.example.zavrsnifull.entities.Like;
import com.example.zavrsnifull.entities.Notification;
import com.example.zavrsnifull.entities.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private NotificationService notificationService;

    public Like addLike(Like like){
        notificationService.saveByPostId(
                LocalDateTime.now(),
                like.getUser().getUsername() + " liked your post.",
                like.getPost().getId()
                );
        return likeRepository.save(like);
    }

    public void delete(Integer postid, Integer userid){
        likeRepository.remove(postid, userid);
    }

    public List<Like> getCount(Integer postid){
        return likeRepository.getCount(postid);
    }

}
