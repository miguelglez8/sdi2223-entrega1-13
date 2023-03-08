package com.uniovi.sdimywallapop.services;

import com.uniovi.sdimywallapop.entities.Conversation;
import com.uniovi.sdimywallapop.entities.Message;
import com.uniovi.sdimywallapop.entities.Offer;
import com.uniovi.sdimywallapop.entities.User;
import com.uniovi.sdimywallapop.repositories.ConversationRepository;
import com.uniovi.sdimywallapop.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private MessageRepository messageRepository;

    public void addConversation(Conversation conversation) {
        conversationRepository.save(conversation);
    }

    public Page<Conversation> getConversations(Pageable pageable) {
        Page<Conversation> conversations = conversationRepository.findAll(pageable);
        return conversations;
    }

    public Conversation searchById(Long id) {
        return conversationRepository.findById(id).get();
    }

    public Conversation searchByBuyerAndOffer(String buyerDni, Long id) {
        return conversationRepository.findByBuyerAndOffer(buyerDni, id);
    }

    public void addMessage(Message message) {
        messageRepository.save(message);
    }

    public List<Message> findMessages(Long id) {
        return messageRepository.findMessages(id);
    }

    public Conversation searchByUserAndOffer(User user, Offer offer){
        return conversationRepository.findByUserAndOffer(user.getDni(), offer.getId());
    }

    public Page<Conversation> searchConversationsTakingPartBy(Pageable pageable, User user) {
        return conversationRepository.findConversationsByUser(pageable, user.getDni());
    }
}
