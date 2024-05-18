package com.example.chatjptbot.bot;

import com.example.chatjptbot.bot.configuration.BotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MyTelegramBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;

    @Autowired
    public MyTelegramBot(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    private String getFormattedDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        return now.format(formatter);
    }

    public void sendMessage(Long chatId, String text) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void deleteMessage(Long chatId, Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId.toString());
        deleteMessage.setMessageId(messageId);

        try {
            execute(deleteMessage);
            System.out.println("Message deleted: " + messageId);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        String formatDateTime = getFormattedDateTime();

        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String messageText = message.getText();
            long chatId = message.getChatId();
            System.out.println(chatId);
            int messageId = message.getMessageId();
            User user = message.getFrom();
            String username = user.getUserName() != null ? user.getUserName() : user.getFirstName();

            Pattern pattern = Pattern.compile("(https?://(?:www\\.)?instagram\\.com/\\S+)");
            Matcher matcher = pattern.matcher(messageText);

            if (matcher.find()) {
                String modifiedMessageText = matcher.replaceAll(m -> {
                    String url = m.group();
                    return url.replace("instagram.com", "ddinstagram.com");
                });

                SendMessage notificationMessage = new SendMessage();
                notificationMessage.setChatId(String.valueOf(chatId));
                notificationMessage.setText("@" + username + " sent Instagram link: " + modifiedMessageText);
                long myChatId = 598389393;
                sendMessage(myChatId, modifiedMessageText);


                try {
                    // Send the notification message
                    execute(notificationMessage);
                    // Delete the original message
                    deleteMessage(chatId, messageId);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.hasInlineQuery()) {
            handleInlineQuery(update.getInlineQuery());
        }
    }

    private void handleInlineQuery(InlineQuery inlineQuery) {
        String formatDateTime = getFormattedDateTime();
        String query = inlineQuery.getQuery();

        List<InlineQueryResult> results = new ArrayList<>();
        Pattern pattern = Pattern.compile("(https?://(?:www\\.)?instagram\\.com/\\S+)");
        Matcher matcher = pattern.matcher(query);

        if (matcher.find()) {
            String modifiedMessageText = matcher.replaceAll(m -> "https://ddinstagram.com/" + m.group().substring(m.group().indexOf("instagram.com/")));

            InputTextMessageContent messageContent = new InputTextMessageContent();
            messageContent.setMessageText(modifiedMessageText);

            InlineQueryResultArticle result = new InlineQueryResultArticle();
            result.setId("1");
            result.setTitle("Modified Instagram Link");
            result.setInputMessageContent(messageContent);
            results.add(result);
        }

        AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
        answerInlineQuery.setInlineQueryId(inlineQuery.getId());
        answerInlineQuery.setResults(results);

        try {
            execute(answerInlineQuery);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }
}
