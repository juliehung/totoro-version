package io.dentall.totoro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneOffset;

@Configuration
public class TimeConfig {

    public static ZoneOffset ZONE_OFF_SET;

    public TimeConfig(@Value("${zoneOffset:Z}") String zoneOffset) {
        ZONE_OFF_SET = ZoneOffset.of(zoneOffset);
    }
}
