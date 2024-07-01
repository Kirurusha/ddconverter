# Используем базовый образ для Java
FROM openjdk:17-jdk-alpine

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем файл сборки проекта в контейнер
COPY target/chatjptbot-0.0.1-SNAPSHOT.jar /app/chatjptbot.jar

# Указываем команду для запуска Spring Boot приложения
ENTRYPOINT ["java", "-jar", "chatjptbot.jar"]
