package com.example.zavrsnifull.controllers;

import com.example.zavrsnifull.entities.Follow;
import com.example.zavrsnifull.services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/follow")
@CrossOrigin
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/new")
    public ResponseEntity<Follow> createNew(@RequestBody Follow follow){
        if (follow == null)
            return ResponseEntity.badRequest().build();
        followService.createNew(follow);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, List<Follow>>> getFollowData(@PathVariable Integer id){
        if (followService.getFollowData(id) == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(followService.getFollowData(id));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Follow> delete(@RequestParam Integer followerid, @RequestParam Integer followeeid){
        if (!followService.delete(followerid, followeeid))
            return ResponseEntity.badRequest().build();
        followService.delete(followerid, followeeid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/isfollowing")
    public ResponseEntity<Boolean> isFollowing(@RequestParam Integer followerid, @RequestParam Integer followeeid){
        return ResponseEntity.ok(followService.isFollowing(followerid, followeeid));
    }
 }
