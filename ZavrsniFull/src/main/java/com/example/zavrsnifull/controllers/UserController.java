package com.example.zavrsnifull.controllers;

import com.example.zavrsnifull.entities.User;
import com.example.zavrsnifull.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/new")
    public ResponseEntity<User> createNew(@RequestPart User user, @RequestPart(required = false) MultipartFile image) throws IOException {
        if(userService.createNew(user, image) == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(userService.createNew(user,image));
    }

    @GetMapping("/getImage/{name}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String name) throws IOException{
        if (userService.getImage(name) == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(userService.getImage(name));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getById(@PathVariable Integer id){
        return ResponseEntity.ok().body(userService.getById(id));
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<List<User>> search(@PathVariable String username){
        return ResponseEntity.ok(userService.search(username));
    }

    @PutMapping("/update/{userid}")
    public ResponseEntity<User> editUser(@PathVariable Integer userid, @RequestPart(required = false) User editedUser, @RequestPart(required = false) MultipartFile image) throws IOException{
        return ResponseEntity.ok(userService.editUser(userid, editedUser, image));
    }

    @PostMapping("/logIn")
    public ResponseEntity<User> logIn(@RequestBody Map<String, String> data){
        if(userService.logIn(data.get("username"), data.get("password")) == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().body(userService.logIn(data.get("username"), data.get("password")));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<User> delete(@RequestParam Integer id){
        if (!userService.delete(id))
            return ResponseEntity.badRequest().build();
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/dajvamo")
    public String daj(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd-HH-mm-ss-SSS");
        String imgName = dateFormat.format(new Date());
        return imgName;
    }
}
