package com.sherman.hearbeat.util;

import com.sherman.hearbeat.mail.MailAccount;

/**
 * Created by IntelliJ IDEA.
 * User: sherman
 * Date: 04.09.11
 * Time: 14:19
 * To change this template use File | Settings | File Templates.
 */
public class ConfigItem {
    private MailAccount account;
    private String name;
    private long delay;

    public MailAccount getAccount() {
        return account;
    }

    public void setAccount(MailAccount account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}
