package com.example.tamakkun.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.ByteArrayInputStream;

@Service
public class EmailService {



    @Autowired
    private JavaMailSender javaMailSender;


    public void sendEmail(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("durr12a8@gmail.com");
        javaMailSender.send(message);
    }


    public void sendEmailWithAttachment(String to, String subject, String text, byte[] attachmentData, String attachmentFileName) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            helper.setFrom("durr12a8@gmail.com");

            // Add QR code attachment
            helper.addAttachment(attachmentFileName, () -> new ByteArrayInputStream(attachmentData));

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email with attachment", e);
        }
    }








}
