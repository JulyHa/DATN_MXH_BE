package com.example.airbnb.service;

import com.example.airbnb.model.Conversation;
import com.example.airbnb.model.Users;

import java.util.List;

public interface IConversationService extends IGeneralService<Conversation> {
    Conversation findPersonalConversation(Long id1, Long id2);
    void createGroupConversation(List<Users> list);
    List<Conversation> getAllPersonalConversation(Long id);
    List<Conversation> getAllGroupConversation(Long id);
    List<Conversation> getALlConversation(Long id);
}

