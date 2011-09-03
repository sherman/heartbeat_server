package com.sherman.hearbeat.server;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 * User: sherman
 * Date: 04.09.11
 * Time: 1:36
 * To change this template use File | Settings | File Templates.
 */
public class HeartbeatHandler extends AbstractHandler {
    private static Logger log = Logger.getLogger(HeartbeatHandler.class);
    private static final String ANSWER = "ok";

    private Timer timer;

    private Date lastNotified;

    private boolean mailWasSent = false;

    public HeartbeatHandler(final long delay) {
        log.info("Timer task created");

        timer = new Timer("notifier");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("Timer task executed");

                if (lastNotified == null || mailWasSent)
                    return;

                log.info("Timer task: " + lastNotified);

                Date now = new Date();
                if (now.getTime() > lastNotified.getTime() + delay) {
                    String host = "smtp.gmail.com";
                    int port = 465;
                    final String username = "user";
                    final String password = "pass";

                    Properties props = new Properties();
                    props.put("mail.smtps.host", host);
                    props.put("mail.smtps.auth", "true");

                    Session session = Session.getDefaultInstance(props);

                    try {
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress("from"));
                        message.setRecipients(
                            Message.RecipientType.TO,
                            InternetAddress.parse("to")
                        );
                        message.setSubject("There is no ping from the algorithm.");
                        message.setText("Last notification from the algorithm was at " + lastNotified.toString());

                        Transport tr = session.getTransport("smtps");
                        tr.connect(host, username, password);
                        tr.sendMessage(message, message.getAllRecipients());
                        tr.close();
                        mailWasSent = true;
                    } catch (MessagingException e) {
                        log.error(e);
                        mailWasSent = false;
                    }
                }
            }
        },
        (delay / 2),
        (delay / 2)
        );
    }

    @Override
    public void handle(
            String target,
            Request request,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) throws IOException, ServletException {
        lastNotified = new Date();
        mailWasSent = false;
        OutputStream out = httpServletResponse.getOutputStream();
        out.write(ANSWER.getBytes());
        out.close();
    }
}
