package com.example.airbnb.controller;

import com.example.airbnb.model.Comments;
import com.example.airbnb.model.Likes;
import com.example.airbnb.model.Posts;
import com.example.airbnb.model.Users;
import com.example.airbnb.service.ILikeService;
import com.example.airbnb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/likes")
public class LikeController {
    @Autowired
    private ILikeService iLikeService;
    @Autowired
    private IUserService iUserService;

    @PostMapping()
    public ResponseEntity<List<Long>> getCountLikePost(@RequestBody Posts[] posts) {
        List<Long> list = new ArrayList<>();
        for (Posts p : posts) {
            list.add(iLikeService.countPostLike(p.getId()));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @PostMapping("/get")
    public ResponseEntity<Long> getCountLikeOnePost(@RequestBody Posts post) {
        return new ResponseEntity<>(iLikeService.countPostLike(post.getId()), HttpStatus.OK);
    }
    @PostMapping("/list")
    public ResponseEntity<?> getListLikeAllPost(@RequestBody Posts[] posts) {
        List<Object> objects = new ArrayList<>();
        for (Posts p : posts) {
            List<Users> usersList = iUserService.findAllLikePost(p.getId());
            objects.add(usersList);
        }
        return new ResponseEntity<>(objects, HttpStatus.OK);
    }

    @PostMapping("/list/get")
    public ResponseEntity<?> getListLikeAllPost(@RequestBody Posts post) {
        List<Users> usersList = iUserService.findAllLikePost(post.getId());
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }
    @PostMapping("/comment/like")
    public ResponseEntity<List<Integer>> getCountLikeComment(@RequestBody Comments[] comments) {
        List<Integer> list = new ArrayList<>();
        for (Comments c : comments) {
            list.add(iLikeService.countCommentLike(c.getId()));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/interact/{id1}/{id2}")
    public ResponseEntity<?> likeOrUnlike(@PathVariable("id1") Long userId, @PathVariable("id2") Long postId){
        if (checkUserLiked(userId, postId)){
            iLikeService.unLikePost(userId, postId);
        }else {
            iLikeService.likePost(userId, postId);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    private boolean checkUserLiked(Long userId, Long postId) {
        Optional<Likes> postLike = iLikeService.findPostLike(userId, postId);
        return postLike.isPresent();
    }

    private boolean checkUserLikedComment(Long userId, Long commentId) {
        Optional<Likes> commentLike = iLikeService.findCommentLike(userId, commentId);
        return commentLike.isPresent();
    }
    @PostMapping("/comment")
    public ResponseEntity<?> countLikeListComment(@RequestBody List<List<Comments>> postComments){
        List<Object> list = new ArrayList<>();
        for (List<Comments> postComment : postComments) {
            List<Integer> integerList = new ArrayList<>();
            for (Comments comments : postComment) {
                integerList.add(iLikeService.countCommentLike(comments.getId()));
            }
            list.add(integerList);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("comment/get")
    public ResponseEntity<?> countLikeListCommentOnePost(@RequestBody List<Comments> postComments){
        List<Integer> integerList = new ArrayList<>();
        for (Comments postComment : postComments) {
            integerList.add(iLikeService.countCommentLike(postComment.getId()));
        }
        return new ResponseEntity<>(integerList, HttpStatus.OK);
    }

    @PostMapping("comment/check/{id}")
    public ResponseEntity<?> checkLikeListComment(@RequestBody List<List<Comments>> postComments, @PathVariable Long id){
        List<Object> list = new ArrayList<>();
        for (List<Comments> postComment : postComments) {
            List<Boolean> booleanList = new ArrayList<>();
            for (Comments comments : postComment) {
                booleanList.add(checkUserLikedComment(id, comments.getId()));
            }
            list.add(booleanList);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("comment/check/get/{id}")
    public ResponseEntity<?> checkLikeComment(@RequestBody List<Comments> postComments, @PathVariable Long id){
        List<Boolean> booleanList = new ArrayList<>();
        for (Comments postComment : postComments) {
            booleanList.add(checkUserLikedComment(id, postComment.getId()));
        }
        return new ResponseEntity<>(booleanList, HttpStatus.OK);
    }
    @GetMapping("/interact/comment/{id1}/{id2}")
    public ResponseEntity<?> likeOrUnlikeComment(@PathVariable("id1") Long userId, @PathVariable("id2") Long cmtId){
        if (checkUserLikedComment(userId, cmtId)){
            iLikeService.unLikeComment(userId, cmtId);
        }else {
            iLikeService.likeComment(userId, cmtId);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
