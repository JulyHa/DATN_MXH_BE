package com.example.airbnb.controller;

import com.example.airbnb.model.Conversation;
import com.example.airbnb.model.Messages;
import com.example.airbnb.model.Users;
import com.example.airbnb.service.IConversationService;
import com.example.airbnb.service.IMessageService;
import com.example.airbnb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private IConversationService iConversationService;
    @Autowired
    private IMessageService iMessageService;
    @Autowired
    private IUserService iUserService;

    @GetMapping("/room/{id1}/{id2}")
    public ResponseEntity<Conversation> getPersonalConversation(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
        return new ResponseEntity<>(iConversationService.findPersonalConversation(id1, id2), HttpStatus.OK);
    }
    @GetMapping("/message/{id}")
    public ResponseEntity<List<Messages>> getMessage(@PathVariable Long id){
        Conversation conversation = iConversationService.findById(id).get();
        List<Messages> messages = iMessageService.findAllByConversation(conversation);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> createMessage(@RequestBody Messages message){
        iMessageService.save(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/group")
    public ResponseEntity<?> createGroupChat(@RequestBody List<Users> users){
        iConversationService.createGroupConversation(users);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<List<Conversation>> getAllPersonalConversation(@PathVariable Long id){
        return new ResponseEntity<>(iConversationService.getAllPersonalConversation(id), HttpStatus.OK);
    }
    @GetMapping("/room/group/{id}")
    public ResponseEntity<List<Conversation>> getAllGroupConversation(@PathVariable Long id){
        return new ResponseEntity<>(iConversationService.getAllGroupConversation(id), HttpStatus.OK);
    }

    @GetMapping("/room/all/{id}")
    public ResponseEntity<List<Conversation>> getAllConversation(@PathVariable Long id){
        return new ResponseEntity<>(iConversationService.getALlConversation(id), HttpStatus.OK);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<List<Users>> searchFriend(@PathVariable Long id, @RequestParam("q") String search){
        return new ResponseEntity<>(iUserService.findInListFriend(id, search), HttpStatus.OK);
    }

    @PostMapping("/member")
    public ResponseEntity<List<Object>> findAllMemberInConversation(@RequestBody List<Conversation> conversations){
        List<Object> users = new ArrayList<>();
        for (Conversation conversation: conversations){
            users.add(iUserService.findMemberByConversation(conversation.getId()));
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping ("/member/{id}")
    public ResponseEntity<List<Users>> findAllMemberInConversation(@PathVariable Long id){
        return new ResponseEntity<>(iUserService.findMemberByConversation(id), HttpStatus.OK);
    }

    @PutMapping("/changeName")
    public ResponseEntity<?> changeNameGroup(@RequestBody Conversation conversation){
        iConversationService.save(conversation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
