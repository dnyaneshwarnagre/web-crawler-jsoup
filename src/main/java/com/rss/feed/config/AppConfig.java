package com.rss.feed.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan({"com.rss.feed.entity"})
@ComponentScan({"com.rss.feed.service"})
public class AppConfig {

    private static String appVersion;

    @Value("${app.version}")
    private void setVersion(String version) {

        appVersion = version;
    }

    public static String getVersion() {
        return appVersion;
    }
}
