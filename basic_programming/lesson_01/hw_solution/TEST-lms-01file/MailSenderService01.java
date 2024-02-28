package com.competa.competademo.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailSenderService {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendMail(@NonNull final String email, @NonNull final String subject, final @NonNull String text) {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        try {
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text,true);
            helper.setFrom("CompetaAdmin");
        } catch (MessagingException e) {
            log.error("Error occurred while sending mail", e);
            throw new IllegalStateException(e);
        }

        javaMailSender.send(message);
    }
}
