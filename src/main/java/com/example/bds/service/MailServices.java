package com.example.bds.service;

import com.example.bds.utils.FnCommon;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServices {

    @Value("${spring.mail.username}")
    private String fromMail;

    private final JavaMailSender mailSender;

    public void sendMail(String toEmail, String otp) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom(fromMail);
        helper.setTo(toEmail);
        helper.setSubject("otp code cua ban");
        helper.setText( " <h3> ma otp cua ban la " + otp + "</h3>", true);
        mailSender.send(mimeMessage);
    }
}
