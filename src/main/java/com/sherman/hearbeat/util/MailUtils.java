package com.sherman.hearbeat.util;

import com.sherman.hearbeat.mail.MailAccount;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: sherman
 * Date: 04.09.11
 * Time: 3:00
 * To change this template use File | Settings | File Templates.
 */
public class MailUtils {
    private MailUtils() {
    }

    public static void sendMail(MailAccount account, String subject, String message) {
        Properties props = new Properties();
        props.put("mail.smtps.host", account.getHost());
        props.put("mail.smtps.auth", "true");

        Session session = Session.getDefaultInstance(props);

        try {
            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(account.getFrom()));
            mimeMessage.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(account.getTo())
            );
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            Transport tr = session.getTransport("smtps");
            tr.connect(account.getHost(), account.getUsername(), account.getPassword());
            tr.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            tr.close();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
