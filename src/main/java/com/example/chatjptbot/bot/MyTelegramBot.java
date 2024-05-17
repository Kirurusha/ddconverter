package com.example.chatjptbot.bot;

import com.example.chatjptbot.bot.configuration.BotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("Received an update");

        // Проверяем, содержит ли обновление сообщение и текст в этом сообщении
        if (update.hasMessage()) {
            System.out.println("Update has a message");

            if (update.getMessage().hasText()) {
                System.out.println("Message has text");

                String messageText = update.getMessage().getText();
                long chatId = update.getMessage().getChatId();
                int messageId = update.getMessage().getMessageId();

                // Создаем объект SendMessage для отправки простого текстового сообщения
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));

                // Проверяем, пришло ли сообщение из группы или личного чата
                if (update.getMessage().getChat().isGroupChat() || update.getMessage().getChat().isSuperGroupChat() || update.getMessage().getChat().isUserChat()) {
                    if (update.getMessage().getChat().isGroupChat() || update.getMessage().getChat().isSuperGroupChat()) {
                        System.out.println("Message is from a group or supergroup chat");
                    } else {
                        System.out.println("Message is from a private chat");
                    }

                    // Ищем ссылки на Instagram в сообщении и заменяем их
                    Pattern pattern = Pattern.compile("(https?://(?:www\\.)?instagram\\.com/\\S+)");
                    Matcher matcher = pattern.matcher(messageText);

                    if (matcher.find()) {
                        System.out.println("Found Instagram link");

                        String modifiedMessageText = matcher.replaceAll(m -> "https://ddinstagram.com/" + m.group().substring(m.group().indexOf("instagram.com/")));
                        message.setText(modifiedMessageText);
                        message.setReplyToMessageId(messageId);

                        try {
                            // Отправка сообщения
                            execute(message);
                            System.out.println("Sent modified message: " + modifiedMessageText);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("No Instagram link found");
                    }
                } else {
                    System.out.println("Message is not from a group, supergroup, or private chat");
                }
            } else {
                System.out.println("Message does not have text");
            }
        } else if (update.hasInlineQuery()) {
            System.out.println("Update has inline query");
            handleInlineQuery(update.getInlineQuery());
        } else {
            System.out.println("Update does not have a message or text");
        }
    }

    private void handleInlineQuery(InlineQuery inlineQuery) {
        String query = inlineQuery.getQuery();
        System.out.println("Handling inline query: " + query);

        // Создаем список результатов для inline-запроса
        List<InlineQueryResult> results = new ArrayList<>();

        // Пример: создаем результат с измененной ссылкой на Instagram
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
            System.out.println("Inline query answered with results");
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
