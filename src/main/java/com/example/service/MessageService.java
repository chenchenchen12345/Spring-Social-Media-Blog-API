package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Message createMessage(Message message) {
        if (message.getMessageText() == null || message.getMessageText().isBlank() || message.getMessageText().length() >= 255) {
            throw new IllegalArgumentException("Message text must be non-blank and under 255 characters.");
        }

        if (!accountRepository.existsById(message.getPostedBy())) {
            throw new IllegalArgumentException("User not found");
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer id) {
        return messageRepository.findById(id).orElse(null);
    }

    public List<Message> getMessagesByAccountId(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }

    public int updateMessageText(Integer id, String newText) {
        Message existing = messageRepository.findById(id).orElse(null);
        if (existing == null) {
            throw new IllegalArgumentException("Message not found");
        }

        if (newText == null || newText.isBlank() || newText.length() >= 255) {
            throw new IllegalArgumentException("Message text must be non-blank and under 255 characters.");
        }

        existing.setMessageText(newText);
        messageRepository.save(existing);
        return 1;
    }

    public int deleteMessageById(Integer id) {
        if (!messageRepository.existsById(id)) {
            return 0;
        }

        messageRepository.deleteById(id);
        return 1;
    }
}
