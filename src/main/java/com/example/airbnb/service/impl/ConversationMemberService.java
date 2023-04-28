package com.example.airbnb.service.impl;

import com.example.airbnb.model.ConversationMember;
import com.example.airbnb.repository.IConversationMemberRepository;
import com.example.airbnb.service.IConversationMemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ConversationMemberService implements IConversationMemberService {

    @Autowired
    private IConversationMemberRepository conversationMemberRepository;

    @Override
    public Iterable<ConversationMember> findAll() {
        return conversationMemberRepository.findAll();
    }

    @Override
    public Optional<ConversationMember> findById(Long id) {
        return conversationMemberRepository.findById(id);
    }

    @Override
    public void save(ConversationMember conversationMember) {
        conversationMemberRepository.save(conversationMember);
    }

    @Override
    public void remove(Long id) {
        conversationMemberRepository.deleteById(id);
    }
}
