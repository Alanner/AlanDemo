package org.csu.workmaster.service;

public interface MailService {
        public void sendHtmlMail(String to, String subject, String content);
        public void sendSimpleMail(String to, String subject, String content);
        public void sendLoginMail(String to);
}
