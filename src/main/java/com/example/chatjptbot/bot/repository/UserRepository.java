package com.example.chatjptbot.bot.repository;

import com.example.chatjptbot.bot.config.HibernateConfig;

import com.example.chatjptbot.bot.entity.UserEntity;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByTelegramId(Long telegramId);


}
