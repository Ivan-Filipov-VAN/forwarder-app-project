package com.softuni.forwardingApp.service;

import com.softuni.forwardingApp.models.view.StatisticViewModel;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;

@Service
public class EmailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    public EmailService(
            TemplateEngine templateEngine,
            JavaMailSender javaMailSender
    ) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    public void sendRegistrationEmail(
            String userEmail,
            String userName
    ) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new
                    MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("5ex_team@5ex.com");
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setSubject("Welcome to 5Ex Forwarding App !");
            mimeMessageHelper.setText(generateMessageContent(userName),
                    true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    private String generateMessageContent(String userName) {
        Context ctx = new Context();
        ctx.setVariable("userName", userName);
        return templateEngine.process("email/registration", ctx);
    }

    public  void sendStatistic(StatisticViewModel statistic) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new
                    MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("5ex_team@5ex.com");
            mimeMessageHelper.setTo("boss@5ex.com");
            mimeMessageHelper.setSubject("Daily Statistic !");
            mimeMessageHelper.setText(generateMessageContent(statistic.getImp(), statistic.getExp()),
                    true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
    public String generateMessageContent(Long imp, Long exp) {
        Context ctx = new Context();
        ctx.setVariable("imp", imp);
        ctx.setVariable("exp", exp);
        ctx.setVariable("date", LocalDate.now().minusDays(1));
        return templateEngine.process("email/statistic", ctx);
    }


}
