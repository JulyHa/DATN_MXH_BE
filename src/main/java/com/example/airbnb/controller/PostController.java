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
    private ICommentService iCommentService;
    @Autowired
    private IFriendService iFriendService;
    @Autowired
    private IPostStatusService postStatusService;

    @PostMapping
    public ResponseEntity<Posts> create(@RequestBody Posts post) {
        iPostService.save(post);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @GetMapping("/status")
    public ResponseEntity<Iterable<PostStatus>> getAllPostStatus() {
        return new ResponseEntity<>(postStatusService.findAll(), HttpStatus.OK);
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
        iPostService.save(post);
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

    private boolean checkUserLikedComment(Long userId, Long commentId) {
        Optional<Likes> commentLike = iLikeService.findCommentLike(userId, commentId);
        return commentLike.isPresent();
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

    @PostMapping("/like")
    public ResponseEntity<List<Long>> getCountLikePost(@RequestBody Posts[] posts) {
        List<Long> list = new ArrayList<>();
        for (Posts p : posts) {
            list.add(iLikeService.countPostLike(p.getId()));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/get/like")
    public ResponseEntity<Long> getCountLikeOnePost(@RequestBody Posts post) {
        return new ResponseEntity<>(iLikeService.countPostLike(post.getId()), HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<List<Long>> getCountCommentPost(@RequestBody Posts[] posts) {
        List<Long> list = new ArrayList<>();
        for (Posts p : posts) {
            list.add(iCommentService.countPostComment(p.getId()));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/get/comment")
    public ResponseEntity<Long> getCountCommentOnePost(@RequestBody Posts post) {
        return new ResponseEntity<>(iCommentService.countPostComment(post.getId()), HttpStatus.OK);
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<List<Comments>> getAllPostComment(@PathVariable Long id) {
        List<Comments> postCommentList = iCommentService.findAllByPost(iPostService.findById(id).get());
        if (postCommentList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(postCommentList, HttpStatus.OK);
    }

    @PostMapping("/comment/like")
    public ResponseEntity<List<Integer>> getCountLikeComment(@RequestBody Comments[] comments) {
        List<Integer> list = new ArrayList<>();
        for (Comments c : comments) {
            list.add(iLikeService.countCommentLike(c.getId()));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
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

    @PostMapping("/list/comment")
    public ResponseEntity<?> getListCommentAllPost(@RequestBody Posts[] posts){
        List<Object> objects = new ArrayList<>();
        for (Posts p : posts) {
            List<Comments> postCommentList = iCommentService.findAllByPost(p);
            objects.add(postCommentList);
        }
        return new ResponseEntity<>(objects, HttpStatus.OK);
    }
    @GetMapping("/wall/{id}/search")
    public ResponseEntity<Iterable<Posts>>searchOnWall(@PathVariable("id") Long id,@RequestParam ("search") String content){
        Iterable<Posts>posts=iPostService.findAllPostByUserIdAndContent(id,content);
        if (!posts.iterator().hasNext()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    @GetMapping("/interact/like/{id1}/{id2}")
    public ResponseEntity<?> likeOrUnlike(@PathVariable("id1") Long userId, @PathVariable("id2") Long postId){
        if (checkUserLiked(userId, postId)){
            iLikeService.unLikePost(userId, postId);
        }else {
            iLikeService.likePost(userId, postId);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/interact/comment")
    public ResponseEntity<?> comment(@RequestBody Comments postComment){
        postComment.setCreateAt(LocalDateTime.now());
        postComment.setCountLike(0L);
        postComment.setStatus(true);
        iCommentService.save(postComment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/interact/comment/{id}")
    public ResponseEntity<Comments> getComment(@PathVariable Long id){
        Optional<Comments> postComment = iCommentService.findById(id);
        return postComment.map(comment -> new ResponseEntity<>(comment, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/interact/comment/like/{id1}/{id2}")
    public ResponseEntity<?> likeOrUnlikeComment(@PathVariable("id1") Long userId, @PathVariable("id2") Long cmtId){
        if (checkUserLikedComment(userId, cmtId)){
            iLikeService.unLikeComment(userId, cmtId);
        }else {
            iLikeService.likeComment(userId, cmtId);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        Optional<Comments> postComment = iCommentService.findById(id);
        if (postComment.isPresent()){
            iCommentService.remove(id);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("comment/{id}")
    public ResponseEntity<?> editComment(@PathVariable Long id, @RequestBody Comments postComment){
        Optional<Comments> postCommentOptional = iCommentService.findById(id);
        if (postCommentOptional.isPresent()){
            postCommentOptional.get().setCreateAt(LocalDateTime.now());
            postCommentOptional.get().setContent(postComment.getContent());
            iCommentService.save(postCommentOptional.get());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("comment/countlike")
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

    @PostMapping("comment/countlike/get")
    public ResponseEntity<?> countLikeListCommentOnePost(@RequestBody List<Comments> postComments){
        List<Integer> integerList = new ArrayList<>();
        for (Comments postComment : postComments) {
            integerList.add(iLikeService.countCommentLike(postComment.getId()));
        }
        return new ResponseEntity<>(integerList, HttpStatus.OK);
    }

    @PostMapping("comment/check/like/{id}")
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

    @PostMapping("comment/check/like/get/{id}")
    public ResponseEntity<?> checkLikeComment(@RequestBody List<Comments> postComments, @PathVariable Long id){
        List<Boolean> booleanList = new ArrayList<>();
        for (Comments postComment : postComments) {
            booleanList.add(checkUserLikedComment(id, postComment.getId()));
        }
        return new ResponseEntity<>(booleanList, HttpStatus.OK);
    }
}
