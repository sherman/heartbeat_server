package com.sherman.hearbeat.server;

import com.sherman.hearbeat.util.Config;
import com.sherman.hearbeat.util.ConfigItem;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;

import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: sherman
 * Date: 04.09.11
 * Time: 1:32
 * To change this template use File | Settings | File Templates.
 */
public class HeartbeatServer {
    private static Logger log = Logger.getLogger(HeartbeatServer.class);

    public static void main(String[] args) {
        List<ConfigItem> config = Config.getConfig();

        if (config.isEmpty())
            log.error("Config hasn't been found.");

        Server server = new Server(4433);

        HandlerCollection handlerCollection = new HandlerCollection();

        for (ConfigItem configItem : config) {
            ContextHandler ctxHandler = new ContextHandler();
            ctxHandler.setContextPath("/" + configItem.getName());
            ctxHandler.setHandler(new HeartbeatHandler(configItem));
            handlerCollection.addHandler(ctxHandler);
        }

        server.setHandler(handlerCollection);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            log.error(e);
        }
    }
}
