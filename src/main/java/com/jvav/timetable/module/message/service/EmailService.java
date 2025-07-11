package com.jvav.timetable.module.message.service;

public interface EmailService {
    void sendMail(String to, String subject, String content);
}