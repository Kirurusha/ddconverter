package com.example.chatjptbot.bot.repository;

import com.example.chatjptbot.bot.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}

