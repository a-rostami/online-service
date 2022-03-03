package com.rostami.onlinehomeservices.service.email;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static com.rostami.onlinehomeservices.exception.messages.ExceptionMessages.FAILED_TO_SEND_EMAIL_MESSAGE;

@Service
@Slf4j
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("arashrostami518@mail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error(FAILED_TO_SEND_EMAIL_MESSAGE, e);
            throw new IllegalStateException(FAILED_TO_SEND_EMAIL_MESSAGE);
        }
    }
}
