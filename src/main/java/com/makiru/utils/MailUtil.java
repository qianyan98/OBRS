package com.makiru.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.Properties;

public class MailUtil extends Thread{
    private String from = "123456@qq.com";
    private String username = from;
    private String password = "123456";
    private String host = "smtp.qq.com";

    private String to;

    private String subject;
    private String content;


    public MailUtil(String to, String subject, String content){
        this.to = to;
        this.subject = subject;
        this.content = content;
    }
    public MailUtil(String to, String from, String subject, String content){
        this.to = to;
        this.from = from;
        this.username = from;
        this.subject = subject;
        this.content = content;
    }

    @Override
    public void run() {
        try {
            Properties properties = new Properties();
            properties.setProperty("mail.host", host);
            properties.setProperty("mail.transport.protocol", "smtp");
            properties.setProperty("mail.smtp.auth", "true");

            //QQ邮箱需要设置SSL加密
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);

            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            });

            session.setDebug(true);

            Transport transport = session.getTransport();
            transport.connect(host, username, password);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(this.to));
            message.setSubject(subject);

            message.setContent(content, "text/html;charset=utf-8");

            transport.sendMessage(message, message.getAllRecipients());

            transport.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
