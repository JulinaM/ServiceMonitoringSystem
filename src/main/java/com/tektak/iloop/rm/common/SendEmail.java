package com.tektak.iloop.rm.common;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


/**
 * Created by tektak on 7/10/14.
 * <p/>
 * This class is for sending email messages
 */
public class SendEmail {
    /**
     * @param receiver Receiver Email ID
     * @param sub      Subject of the email
     * @param msg      Message of the email
     *                 Message is sent in html format
     */
    public static void email(String receiver, String sub, String msg) {
        String to = receiver;
        String from = "rmloop92@gmail.com";
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "587");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("rmloop92@gmail.com", "pass_rmloop92");
            }
        });
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg, "utf-8", "html");
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}