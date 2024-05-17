package com.example.chatjptbot.bot.configuration;



import com.example.chatjptbot.bot.MyTelegramBot;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {

    @Value("${bot.username}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;
    @Bean
    public TelegramBotsApi telegramBotsApi(MyTelegramBot myTelegramBot) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(myTelegramBot);
        return api;
    }

    // Добавьте этот бин, если вы планируете использовать OkHttpClient в вашем приложении
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }
}
