package com.example.chatjptbot.bot.service;

import org.jvnet.hk2.annotations.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final TelegramLongPollingBot bot;

    public MessageService(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    public void sendSubscribeButton(Long chatId) {
        SendMessage message = new SendMessage();
        message.setText("Подпишитесь на наш канал, чтобы получать последние обновления!");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton subscribeButton = new InlineKeyboardButton();
        subscribeButton.setText("Subscribe");
        subscribeButton.setUrl("http");
        rowInline.add(subscribeButton);
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        try {
            bot.execute(message); // Отправка сообщения
        } catch ( TelegramApiException e) {
            e.printStackTrace();
        }


    }
}
