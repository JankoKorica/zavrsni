package com.example.zavrsnifull.controllers;

import com.example.zavrsnifull.entities.Post;
import com.example.zavrsnifull.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/post")
@CrossOrigin
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/new")
    public ResponseEntity<Post> createNew(@RequestPart Post post, @RequestPart(required = false) MultipartFile image) throws IOException {
        if(post == null)
            return ResponseEntity.badRequest().build();
        postService.createNew(post,image);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getby/{what}")
    public ResponseEntity<List<Post>> getByUserId(@PathVariable String what,@RequestParam Integer id, @RequestParam Integer offset){
        if (postService.getBy(what, id, offset) == null)
            return ResponseEntity.badRequest().build();
        return  ResponseEntity.ok(postService.getBy(what, id, offset));
    }

    @GetMapping("/getImage/{name}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String name) throws IOException{
        if (name.equals("no-image"))
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).build();
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(postService.getImage(name));

    }

}
