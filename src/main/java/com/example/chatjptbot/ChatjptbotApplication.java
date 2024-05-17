package com.example.chatjptbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.example.chatjptbot.bot.MyTelegramBot;

@SpringBootApplication
public class ChatjptbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatjptbotApplication.class, args);
	}
}
