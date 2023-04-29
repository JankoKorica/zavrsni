package com.example.zavrsnifull.controllers;

import com.example.zavrsnifull.entities.Comment;
import com.example.zavrsnifull.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/new")
    public ResponseEntity<Comment> createNew(@RequestBody Comment comment){
        if (comment == null)
            return ResponseEntity.badRequest().build();
        commentService.createNew(comment);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllByPost/{id}")
    public ResponseEntity<List<Comment>> getAllByPost(@PathVariable Integer id){
        if (commentService.getAllByPost(id) == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(commentService.getAllByPost(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Comment> delete(@PathVariable Integer id){
        if (!commentService.delete(id))
            return ResponseEntity.badRequest().build();
        commentService.delete(id);
        return ResponseEntity.ok().build();
    }
}
