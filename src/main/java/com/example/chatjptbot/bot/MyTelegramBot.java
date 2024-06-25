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

    public void sendMessage(SendMessage message) {
        try {
            execute(message);
            System.out.println("Message sent " + message.getText());

        }catch (TelegramApiException e) {
            e.printStackTrace();}
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


            // Создаем объект SendMessage для отправки простого текстового сообщения
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(chatId));

            // Проверяем команды /start и /help
            if (messageText.equals("/start") || messageText.equals("/start@" + getBotUsername())) {
                sendMessage.setText(
                        "Welcome to our bot!\n\n" +
                                "This bot helps you convert Instagram links by changing them to https://ddinstagram.com/.\n\n" +
                                "How to use the bot:\n" +
                                "1. **Personal chat with the bot**: Simply send an Instagram link, and the bot will automatically change it.\n" +
                                "2. **Adding the bot to a group**: Add the bot to your group and grant it admin rights to delete messages. Send an Instagram link in the group, and the bot will change it.\n\n" +
                                "For group use:\n" +
                                "- Ensure the bot has admin rights to delete the original messages with Instagram links.\n" +
                                "Inline queries:\n" +
                                "You can use inline queries for quick conversion of Instagram links without leaving the current chat. Just type @Instagram_converter_bot and the Instagram link, and the bot will suggest the modified link.\n\n" +
                                "If you have any questions or need help, use the /help command for more information.\n\n" +

                                "Добро пожаловать в нашего бота!\n\n" +
                                "Этот бот помогает вам преобразовывать ссылки на Instagram, заменяя их на https://ddinstagram.com/.\n\n" +
                                "Как использовать бота:\n" +
                                "1. **Личный чат с ботом**: Просто отправьте ссылку на Instagram, и бот автоматически изменит её.\n" +
                                "2. **Добавление бота в группу**: Добавьте бота в вашу группу и предоставьте ему права администратора для удаления сообщений. Отправьте ссылку на Instagram в группе, и бот изменит её.\n\n" +
                                "Для использования бота в группе:\n" +
                                "- Убедитесь, что у бота есть права администратора, чтобы он мог удалять оригинальные сообщения с ссылками на Instagram.\n" +
                                "Инлайн запросы:\n" +
                                "Вы можете использовать инлайн запросы для быстрого преобразования ссылок на Instagram, не выходя из текущего чата. Просто введите @Instagram_converter_bot и ссылку на Instagram, и бот предложит вам измененную ссылку.\n\n" +
                                "Если у вас есть вопросы или нужна помощь, используйте команду /help для получения дополнительной информации."
                ); sendMessage(sendMessage);
            } else if (messageText.equals("/help")|| messageText.equals("/help@" + getBotUsername())) {
                sendMessage.setText(
                        "How to use the bot:\n" +
                                "1. **Personal chat with the bot**: Simply send an Instagram link, and the bot will automatically change it.\n" +
                                "2. **Adding the bot to a group**: Add the bot to your group and grant it admin rights to delete messages. Send an Instagram link in the group, and the bot will change it.\n\n" +
                                "Inline queries:\n" +
                                "You can use inline queries for quick conversion of Instagram links without leaving the current chat. Just type @Instagram_converter_bot and the Instagram link, and the bot will suggest the modified link.\n\n" +
                                "If you have any questions or need help, use the /help command for more information.\n\n" +

                                "Как использовать бота:\n" +
                                "1. **Личный чат с ботом**: Просто отправьте ссылку на Instagram, и бот автоматически изменит её.\n" +
                                "2. **Добавление бота в группу**: Добавьте бота в вашу группу и предоставьте ему права администратора для удаления сообщений. Отправьте ссылку на Instagram в группе, и бот изменит её.\n\n" +
                                "Инлайн запросы:\n" +
                                "Вы можете использовать инлайн запросы для быстрого преобразования ссылок на Instagram, не выходя из текущего чата. Просто введите @Instagram_converter_bot и ссылку на Instagram, и бот предложит вам измененную ссылку.\n\n" +
                                "Если у вас есть вопросы или нужна помощь, используйте команду /help для получения дополнительной информации."
                );
                sendMessage(sendMessage);
            }else{


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
