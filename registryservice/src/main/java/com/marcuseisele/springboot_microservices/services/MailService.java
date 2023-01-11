package com.server.datn.server.services;

public interface MailService {
    boolean sendEmail(String email, String content, String title);
}
