package com.sherman.hearbeat.server;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;


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
        Server server = new Server(4433);
        server.setHandler(new HearbeatHandler(5000));
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            log.error(e);
        }
    }
}
