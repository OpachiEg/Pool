package com.pool.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.TimeZone;

@Configuration
public class TimeConfig {

    @PostConstruct
    public void setDefaultTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
    }

}
