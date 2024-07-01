package com.example.chatjptbot.bot.service;

import com.example.chatjptbot.bot.entity.Subscription;
import com.example.chatjptbot.bot.entity.UserEntity;
import com.example.chatjptbot.bot.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {

    private  final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public void save(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    public Subscription findById(Long id) {
        return subscriptionRepository.findById(id).orElse(null);
    }

    public List<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }

    public void deleteById(Long id) {
        subscriptionRepository.deleteById(id);
    }





}
