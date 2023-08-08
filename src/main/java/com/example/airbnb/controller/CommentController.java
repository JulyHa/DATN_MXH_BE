package com.example.airbnb.controller;

import com.example.airbnb.model.Comments;
import com.example.airbnb.model.Posts;
import com.example.airbnb.service.ICommentService;
import com.example.airbnb.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private ICommentService iCommentService;
    @Autowired
    private IPostService iPostService;

    @PostMapping()
    public ResponseEntity<List<Long>> getCountCommentPost(@RequestBody Posts[] posts) {
        List<Long> list = new ArrayList<>();
        for (Posts p : posts) {
            list.add(iCommentService.countPostComment(p.getId()));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @PostMapping("/getAll")
    public ResponseEntity<Long> getCountCommentOnePost(@RequestBody Posts post) {
        return new ResponseEntity<>(iCommentService.countPostComment(post.getId()), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<Comments>> getAllPostComment(@PathVariable Long id) {
        List<Comments> postCommentList = iCommentService.findAllByPost(iPostService.findById(id).get());
        if (postCommentList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(postCommentList, HttpStatus.OK);
    }
    @PostMapping("/list")
    public ResponseEntity<?> getListCommentAllPost(@RequestBody Posts[] posts){
        List<Object> objects = new ArrayList<>();
        for (Posts p : posts) {
            List<Comments> postCommentList = iCommentService.findAllByPost(p);
            objects.add(postCommentList);
        }
        return new ResponseEntity<>(objects, HttpStatus.OK);
    }
    @PostMapping("/interact")
    public ResponseEntity<?> comment(@RequestBody Comments postComment){
        postComment.setCreateAt(LocalDateTime.now());
        postComment.setCountLike(0L);
        postComment.setStatus(true);
        iCommentService.save(postComment);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/interact/{id}")
    public ResponseEntity<Comments> getComment(@PathVariable Long id){
        Optional<Comments> postComment = iCommentService.findById(id);
        return postComment.map(comment -> new ResponseEntity<>(comment, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        Optional<Comments> postComment = iCommentService.findById(id);

        if (postComment.isPresent()){
            iCommentService.remove(id);
            Posts posts = iPostService.findById(postComment.get().getIdParent()).get();
            posts.setCountComment(posts.getCountComment()-1);
            iPostService.save(posts);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editComment(@PathVariable Long id, @RequestBody Comments postComment){
        Optional<Comments> postCommentOptional = iCommentService.findById(id);
        if (postCommentOptional.isPresent()){
            postCommentOptional.get().setCreateAt(LocalDateTime.now());
            postCommentOptional.get().setContent(postComment.getContent());
            iCommentService.save(postCommentOptional.get());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
