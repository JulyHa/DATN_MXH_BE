package com.example.airbnb.service;
import com.example.airbnb.model.Conversation;
import com.example.airbnb.model.Messages;

import java.util.List;

public interface IMessageService extends IGeneralService<Messages> {
    List<Messages> findAllByConversation(Conversation conversation);
}
