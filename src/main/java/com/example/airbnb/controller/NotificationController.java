package com.example.airbnb.controller;

import com.example.airbnb.model.Notifications;
import com.example.airbnb.service.ICommentService;
import com.example.airbnb.service.ILikeService;
import com.example.airbnb.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private INotificationService notificationService;
    @Autowired
    private ILikeService iLikeService;
    @Autowired
    private ICommentService iCommentService;

    @GetMapping("/seen/{id}")
    public ResponseEntity<?> seenNotification(@PathVariable Long id) {
        Notifications notification = notificationService.findById(id).get();
        notification.setStatus(true);
        notificationService.save(notification);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<Notifications>> getAll(@PathVariable Long id) {
        List<Notifications> notifications = notificationService.getAll(id);
        Collections.sort(notifications, Comparator.comparing(Notifications::getNotificationAt).reversed());
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }
    @PostMapping("/other")
    public ResponseEntity<List<Long>> other(@RequestBody Notifications[] notifications){
        List<Long> list = new ArrayList<>();
        for (Notifications n: notifications){
            if (n.getNotificationType().getId() == 2) {
                list.add(iLikeService.countPostLike(n.getPost().getId()));
            }else {
                if (n.getNotificationType().getId() == 3){
                    list.add(iCommentService.countUserCommentPost(n.getPost().getId()));
                }else {
                    list.add(1L);
                }
            }
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
