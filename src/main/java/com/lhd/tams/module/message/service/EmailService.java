package com.lhd.tams.module.message.service;

public interface EmailService {
    void sendMail(String to, String subject, String content);
}