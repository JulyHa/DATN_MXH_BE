package com.example.airbnb.service.impl;

import com.example.airbnb.model.Conversation;
import com.example.airbnb.model.Messages;
import com.example.airbnb.repository.IConversationRepository;
import com.example.airbnb.repository.IMessageRepository;
import com.example.airbnb.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService implements IMessageService {
    @Autowired
    private IMessageRepository messageRepository;
    @Autowired
    private IConversationRepository conversationRepository;

    @Override
    public Iterable<Messages> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Optional<Messages> findById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public void save(Messages message) {
        Conversation conversation = message.getConversation();
        conversation.setStatus(false);
        conversationRepository.save(conversation);
        message.setSendTime(LocalDateTime.now());
        messageRepository.save(message);
    }

    @Override
    public void remove(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public List<Messages> findAllByConversation(Conversation conversation) {
        return messageRepository.findAllByConversation(conversation);
    }
}
