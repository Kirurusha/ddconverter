package com.example.chatjptbot.bot.service;

import com.example.chatjptbot.bot.entity.SubscriptionHistory;
import com.example.chatjptbot.bot.repository.SubscriptionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionHistoryService {

    private final SubscriptionHistoryRepository subscriptionHistoryRepository;
    @Autowired
    public SubscriptionHistoryService(SubscriptionHistoryRepository subscriptionHistoryRepository) {
        this.subscriptionHistoryRepository = subscriptionHistoryRepository;
    }
    public void save(SubscriptionHistory subscriptionHistory) {
        subscriptionHistoryRepository.save(subscriptionHistory);
    }

    public SubscriptionHistory findById(Long id) {
        return subscriptionHistoryRepository.findById(id).orElse(null);
    }

    public List<SubscriptionHistory> findAll() {
        return subscriptionHistoryRepository.findAll();
    }

    public void deleteById(Long id) {
        subscriptionHistoryRepository.deleteById(id);
    }


}
