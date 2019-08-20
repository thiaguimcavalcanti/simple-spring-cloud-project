package com.bot.exchanges.commons.enums;

import java.time.Duration;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;

public enum PeriodEnum {

	ONE_MIN(Duration.ofSeconds(60)),
	FIVE_MIN(Duration.ofSeconds(300)),
	FIFTEEN_MIN(Duration.ofSeconds(900)),
	THIRTY_MIN(Duration.ofSeconds(1800)),
	ONE_HOUR(Duration.ofSeconds(3600)),
	ONE_DAY(Period.ofDays(1));

	private TemporalAmount duration;

	private PeriodEnum(TemporalAmount duration) {
		this.duration = duration;
	}

	public TemporalAmount getDuration() {
		return duration;
	}

	public long getInSeconds() {
		return duration.get(ChronoUnit.SECONDS);
	}

	public long getInMinutes() {
		return getInSeconds() / 60;
	}
}
