package com.server.datn.server.services.impl;

import com.server.datn.server.services.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {
    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    private final JavaMailSender mailSender;
    private boolean isSuccess;


    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public boolean sendEmail(String email, String content, String title) {
        Thread thread = new Thread(() -> {
            try {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true, "UTF-8");
                messageHelper.setTo(email);
                messageHelper.setSubject(title);
                messageHelper.setText(content);

                mailSender.send(mimeMessage);
                isSuccess = true;
            } catch (MessagingException e) {
                log.error("SEND MAIL EXCEPTION: ", e);
                isSuccess = false;
            }
        });
        thread.start();
        return isSuccess;
    }
}
