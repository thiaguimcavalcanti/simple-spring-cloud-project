package com.bot.commons.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;

public class DateUtils {

    private static final ZoneId DEFAULT_ZONE_OFFSET = ZoneId.of("UTC");

    public static final DateTimeFormatter DATETIME_WITH_T_FORMAT_DESERIALIZE = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static ZonedDateTime convertEpochMilliToZonedDateTime(String time) {
        Instant instant = Instant.ofEpochMilli(Long.parseLong(time));
        return ZonedDateTime.ofInstant(instant, DEFAULT_ZONE_OFFSET);
    }

    public static ZonedDateTime convertEpochSecondToZonedDateTime(String time) {
        Instant instant = Instant.ofEpochSecond(Long.parseLong(time));
        return ZonedDateTime.ofInstant(instant, DEFAULT_ZONE_OFFSET);
    }

    public static ZonedDateTime convertDateToZonedDateTime(String time, DateTimeFormatter dateTimeFormatter) {
        time = time.split("\\.")[0].replaceAll("\"", "");
        LocalDateTime datetime = LocalDateTime.parse(time, dateTimeFormatter);
        return datetime.atZone(DEFAULT_ZONE_OFFSET);
    }

    public static ZonedDateTime roundZonedDateTime(ZonedDateTime zonedDateTime, TemporalAmount tickDuration) {
        return zonedDateTime.minusMinutes(zonedDateTime.getMinute() % (tickDuration.get(ChronoUnit.SECONDS) / 60))
                .withSecond(0)
                .withNano(0);
    }
}
