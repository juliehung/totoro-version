package io.dentall.totoro.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.ZoneOffset;

@Configuration
public class TimeConfig {

    public static ZoneOffset ZONE_OFF_SET;

    public static Instant MINIMUM_INSTANT;

    public TimeConfig(@Value("${zoneOffset:Z}") String zoneOffset) {
        ZONE_OFF_SET = ZoneOffset.of(zoneOffset);
        MINIMUM_INSTANT = Instant.parse("1900-01-01T00:00:00Z").atOffset(ZoneOffset.of(zoneOffset)).toInstant();
    }
}
