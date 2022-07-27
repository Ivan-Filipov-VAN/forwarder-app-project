package com.softuni.forwardingApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender(
            @Value("${spring.mail.host}") String mailHost,
            @Value("${spring.mail.port}") Integer mailPort,
            @Value("${spring.mail.username}") String userName,
            @Value("${spring.mail.password}") String mailPassword
    ) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailHost);
        javaMailSender.setPort(mailPort);
        javaMailSender.setUsername(userName);
        javaMailSender.setPassword(mailPassword);
        javaMailSender.setJavaMailProperties(mailProperties());
        return javaMailSender;
    }
    private Properties mailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.transport.protocol", "smtp");
        return properties;
    }
}