package com.example.zavrsnifull.services;

import com.example.zavrsnifull.Repositiries.CommentRepository;
import com.example.zavrsnifull.Repositiries.NotificationRepository;
import com.example.zavrsnifull.Repositiries.PostRepository;
import com.example.zavrsnifull.entities.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NotificationService notificationService;

    public Comment createNew(Comment comment){
        notificationService.saveByPostId(
                LocalDateTime.now(),
                comment.getUser().getUsername() + " comented your post.",
                comment.getPost().getId()
                );
        return commentRepository.save(comment);
    }

    public List<Comment> getAllByPost(Integer id){
        return commentRepository.getAllByPost(id);
    }

    public boolean delete(Integer id){
        if (commentRepository.existsById(id))
            return false;
        commentRepository.deleteById(id);
        return true;
    }
}
