package com.example.airbnb.controller;

import com.example.airbnb.dto.PostDisplay;
import com.example.airbnb.model.*;
import com.example.airbnb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private IPostService iPostService;
    @Autowired
    private IPostImageService iPostImageService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ILikeService iLikeService;
    @Autowired
    private IFriendService iFriendService;
    @Autowired
    private IPostStatusService iPostStatusService;
    @Autowired
    private INotificationService iNotificationService;

    @PostMapping
    public ResponseEntity<Posts> create(@RequestBody Posts posts) {
        posts.setCreateAt(LocalDateTime.now());
        posts.setStatus(true);
        posts.setCountLikePost(0L);
        posts.setCountComment(0L);
        posts.setStatus(true);
        iPostService.save(posts);
        return new ResponseEntity<>(posts, HttpStatus.CREATED);
    }

    @GetMapping("/status")
    public ResponseEntity<Iterable<PostStatus>> getAllPostStatus() {
        return new ResponseEntity<>(iPostStatusService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/create/img")
    public ResponseEntity<?> createImg(@RequestBody PostImage[] imagePostList) {
        for (PostImage imagePost : imagePostList) {
            iPostImageService.save(imagePost);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updatePost(@RequestBody Posts post) {
        Optional<Posts> postOptional = iPostService.findById(post.getId());
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        iPostService.update(post);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        iPostService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/image")
    public ResponseEntity<?> updateImgPost(@RequestBody List<Long> deleteList){
        for (Long i: deleteList){
            iPostImageService.remove(i);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PostDisplay>> getAllPostNewFeed(@PathVariable Long id) {
        List<Users> users = iUserService.findFriendRequestsByIdAndStatusTrue(id);
        List<PostDisplay> postDisplays = new ArrayList<>();
        List<Posts> posts = new ArrayList<>();
        for (Users u : users) {
            posts.addAll(iPostService.findAllFriendPost(u.getId()));
        }
        posts.addAll(iPostService.findAllPersonalPost(iUserService.findById(id).get()));
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (Posts post : posts) {
            transferPostDisplay(postDisplays, post);
        }
        for (PostDisplay p : postDisplays) {
            p.setCheckUserLiked(checkUserLiked(id, p.getId()));
        }
        Collections.sort(postDisplays, Comparator.comparing(PostDisplay::getCreateAt).reversed());
        return new ResponseEntity<>(postDisplays, HttpStatus.OK);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<List<PostDisplay>> getAllPostProfile(@PathVariable Long id) {
        List<PostDisplay> postDisplays = new ArrayList<>();
        for (Posts post : iPostService.findAllPersonalPost(iUserService.findById(id).get())) {
            transferPostDisplay(postDisplays, post);
        }
        for (PostDisplay p : postDisplays) {
            p.setCheckUserLiked(checkUserLiked(id, p.getId()));
        }
        Collections.reverse(postDisplays);
        return new ResponseEntity<>(postDisplays, HttpStatus.OK);
    }

    @GetMapping("/wall/{id1}/{id2}")
    public ResponseEntity<List<PostDisplay>> getAllPostWallFriend(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
        //id1 cua friend, id2 cua nguoi dang nhap
        List<PostDisplay> postDisplays = new ArrayList<>();
        Optional<Friends> friendRequest = iFriendService.findFriendRequest(id1, id2);
        if (friendRequest.isPresent()) {
            for (Posts post : iPostService.findAllFriendPost(id1)) {
                transferPostDisplay(postDisplays, post);
            }
        } else {
            for (Posts post : iPostService.findAllFriendPublicPost(id1)) {
                transferPostDisplay(postDisplays, post);
            }
        }
        for (PostDisplay p : postDisplays) {
            p.setCheckUserLiked(checkUserLiked(id2, p.getId()));
        }
        Collections.reverse(postDisplays);
        return new ResponseEntity<>(postDisplays, HttpStatus.OK);
    }


    private void transferPostDisplay(List<PostDisplay> postDisplays, Posts post) {
        PostDisplay postDisplay = new PostDisplay();
        postDisplay.setId(post.getId());
        postDisplay.setContent(post.getContent());
        postDisplay.setPostStatus(post.getPostStatus());
        postDisplay.setUsers(post.getUsers());
        postDisplay.setCreateAt(post.getCreateAt());
        postDisplays.add(postDisplay);
    }

    @GetMapping("/display/get/{id1}/{id2}")
    public ResponseEntity<PostDisplay> getPostDisplay(@PathVariable("id1") Long id1,@PathVariable("id2") Long id2){
        Posts post = iPostService.findById(id1).get();
        PostDisplay postDisplay = new PostDisplay();
        postDisplay.setId(post.getId());
        postDisplay.setContent(post.getContent());
        postDisplay.setPostStatus(post.getPostStatus());
        postDisplay.setUsers(post.getUsers());
        postDisplay.setCreateAt(post.getCreateAt());
        postDisplay.setCheckUserLiked(checkUserLiked(id2, postDisplay.getId()));
        return new ResponseEntity<>(postDisplay, HttpStatus.OK);
    }

    private boolean checkUserLiked(Long userId, Long postId) {
        Optional<Likes> postLike = iLikeService.findPostLike(userId, postId);
        return postLike.isPresent();
    }

    @PostMapping("/image")
    public ResponseEntity<?> getImg(@RequestBody Posts[] posts) {
        List<Object> objects = new ArrayList<>();
        for (Posts p : posts) {
            List<PostImage> imagePosts = iPostImageService.findAllByPost(p);
            objects.add(imagePosts);
        }
        return new ResponseEntity<>(objects, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Posts> getOne(@PathVariable Long id) {
        return new ResponseEntity<>(iPostService.findById(id).get(), HttpStatus.OK);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<List<PostImage>> getImagePost(@PathVariable Long id) {
        return new ResponseEntity<>(iPostImageService.findAllByPost(iPostService.findById(id).get()), HttpStatus.OK);
    }

    @PostMapping("/list/like")
    public ResponseEntity<?> getListLikeAllPost(@RequestBody Posts[] posts) {
        List<Object> objects = new ArrayList<>();
        for (Posts p : posts) {
            List<Users> usersList = iUserService.findAllLikePost(p.getId());
            objects.add(usersList);
        }
        return new ResponseEntity<>(objects, HttpStatus.OK);
    }

    @PostMapping("/list/get/like")
    public ResponseEntity<?> getListLikeAllPost(@RequestBody Posts post) {
        List<Users> usersList = iUserService.findAllLikePost(post.getId());
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    @GetMapping("/wall/{id}/search")
    public ResponseEntity<Iterable<Posts>>searchOnWall(@PathVariable("id") Long id,@RequestParam ("search") String content){
        Iterable<Posts>posts=iPostService.findAllPostByUserIdAndContent(id,content);
        if (!posts.iterator().hasNext()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }return new ResponseEntity<>(posts,HttpStatus.OK);
    }
    @GetMapping("/search/{id}/{search}")
    public ResponseEntity<List<PostDisplay>> searchPost(@PathVariable Long id, @PathVariable("search") String search) {
        List<Users> users = iUserService.findFriendRequestsByIdAndStatusTrue(id);
        List<PostDisplay> postDisplays = new ArrayList<>();
        List<Posts> posts = new ArrayList<>();
        search = "%"+search+"%";
        for (Users u : users) {
            posts.addAll(iPostService.searchPost(u.getId(), search));
        }
        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (Posts post : posts) {
            transferPostDisplay(postDisplays, post);
        }
        for (PostDisplay p : postDisplays) {
            p.setCheckUserLiked(checkUserLiked(id, p.getId()));
        }
        Collections.sort(postDisplays, Comparator.comparing(PostDisplay::getCreateAt).reversed());
        return new ResponseEntity<>(postDisplays, HttpStatus.OK);
    }

}
