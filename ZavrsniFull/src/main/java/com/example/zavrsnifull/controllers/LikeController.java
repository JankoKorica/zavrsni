package com.example.zavrsnifull.controllers;

import com.example.zavrsnifull.entities.Like;
import com.example.zavrsnifull.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/like")
@CrossOrigin
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/new")
    public ResponseEntity<Like> addLike(@RequestBody Like like){
        if (likeService.addLike(like) == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<Like>> getCount(@PathVariable Integer id){
        return ResponseEntity.ok(likeService.getCount(id));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Integer userid, @RequestParam Integer postid){
        if (userid == null || postid == null)
            return ResponseEntity.badRequest().build();
        likeService.delete(postid, userid);
        return ResponseEntity.ok().build();
    }

}
