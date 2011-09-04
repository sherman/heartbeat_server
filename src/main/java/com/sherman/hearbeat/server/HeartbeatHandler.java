package com.sherman.hearbeat.server;

import com.sherman.hearbeat.mail.MailAccount;
import com.sherman.hearbeat.util.ConfigItem;
import com.sherman.hearbeat.util.MailUtils;
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
    private final ConfigItem config;

    public HeartbeatHandler(final ConfigItem config) {
        this.config = config;
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
                if (now.getTime() > lastNotified.getTime() + config.getDelay()) {
                    try {
                        MailUtils.sendMail(
                            config.getAccount(),
                            "There is no ping from the algorithm.",
                            "Last notification from the algorithm was at " + lastNotified.toString()
                        );
                        mailWasSent = true;
                    } catch (RuntimeException e) {
                        log.error(e);
                        mailWasSent = false;
                    }
                }
            }
        },
        (config.getDelay() / 2),
        (config.getDelay() / 2)
        );
    }

    @Override
    public void handle(
            String target,
            Request request,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) throws IOException, ServletException {
        log.info(target);
        lastNotified = new Date();
        mailWasSent = false;
        OutputStream out = httpServletResponse.getOutputStream();
        out.write(ANSWER.getBytes());
        out.close();
    }
}
