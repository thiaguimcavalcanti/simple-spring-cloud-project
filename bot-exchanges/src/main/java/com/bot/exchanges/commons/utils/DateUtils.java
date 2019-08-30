package com.bot.exchanges.commons.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.function.BiFunction;

public class DateUtils {

    private static final ZoneId DEFAULT_ZONE_OFFSET = ZoneId.of("UTC");

    private static final DateTimeFormatter DATETIME_WITH_T_FORMAT_DESERIALIZE = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public static final BiFunction<String, TemporalAmount, ZonedDateTime> PARSE_BINANCE_DATE = (time, tickDuration) -> {
        Instant instant = Instant.ofEpochMilli(Long.parseLong(time));
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, DEFAULT_ZONE_OFFSET).plus(tickDuration);
        return zonedDateTime.minusMinutes(zonedDateTime.getMinute() % (tickDuration.get(ChronoUnit.SECONDS) / 60))
                .withSecond(0)
                .withNano(0);
    };

    public static final BiFunction<String, TemporalAmount, ZonedDateTime> PARSE_BITTREX_DATE = (time, tickDuration) -> {
        time = time.split("\\.")[0].replaceAll("\"", "");
        LocalDateTime datetime = LocalDateTime.parse(time, DATETIME_WITH_T_FORMAT_DESERIALIZE);
        return datetime.atZone(DEFAULT_ZONE_OFFSET).plus(tickDuration);
    };
}
