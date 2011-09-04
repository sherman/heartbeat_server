package com.sherman.hearbeat.util;

import com.sherman.hearbeat.mail.MailAccount;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: sherman
 * Date: 04.09.11
 * Time: 14:18
 * To change this template use File | Settings | File Templates.
 */
public class Config {
    private static final Logger log = Logger.getLogger(Config.class);

    private Config() {
    }

    public static List<ConfigItem> getConfig() {
        InputStream in = Config.class.getClassLoader().getResourceAsStream("accounts");

        List<ConfigItem> configItemList = new ArrayList<ConfigItem>();

        if (in == null)
            return configItemList;

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String configElt;

        try {
            while ((configElt = reader.readLine()) != null) {
                String[] parts = configElt.split(",");

                ConfigItem item = new ConfigItem();
                item.setName(parts[0].trim());
                item.setDelay(Long.parseLong(parts[1].trim()));
                item.setAccount(
                    MailAccount.create().
                        setHost(parts[2].trim()).
                        setPort(Integer.parseInt(parts[3].trim())).
                        setUsername(parts[4].trim()).
                        setPassword(parts[5].trim()).
                        setFrom(parts[6].trim()).
                        setTo(parts[7].trim())
                    );

                configItemList.add(item);
            }
        } catch (IOException e) {
            log.error(e);
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error(e);
                }
        }

        return configItemList;
    }
}
