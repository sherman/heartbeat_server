package com.sherman.hearbeat.mail;

/**
 * Created by IntelliJ IDEA.
 * User: sherman
 * Date: 04.09.11
 * Time: 3:02
 * To change this template use File | Settings | File Templates.
 */
public class MailAccount {
    private String host;
    private int port;
    private String username;
    private String password;
    private String from;
    private String to;

    public static MailAccount create() {
        return new MailAccount();
    }

    public String getHost() {
        return host;
    }

    public MailAccount setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public MailAccount setPort(int port) {
        this.port = port;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public MailAccount setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MailAccount setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public MailAccount setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getTo() {
        return to;
    }

    public MailAccount setTo(String to) {
        this.to = to;
        return this;
    }
}
