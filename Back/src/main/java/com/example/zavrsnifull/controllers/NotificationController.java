package com.example.zavrsnifull.controllers;

import com.example.zavrsnifull.entities.Notification;
import com.example.zavrsnifull.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@CrossOrigin
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/get/{userid}")
    public ResponseEntity<List<Notification>> getByUser(@PathVariable Integer userid){
        if (userid == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(notificationService.getByUser(userid));
    }

    @PutMapping("/viewed/{id}")
    public ResponseEntity<Notification> setViewed(@PathVariable Integer id){
        notificationService.setVeiwed(id);
        return ResponseEntity.ok().build();
    }
}
