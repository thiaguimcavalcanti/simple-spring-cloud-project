package com.bot.commons.enums;

import java.util.Arrays;

public enum OrderStatusEnum {
	READY_TO_START,
	NEW,
	PARTIALLY_FILLED,
	FILLED,
	CANCELED,
	REJECTED,
	EXPIRED;

	public static boolean isClosed(OrderStatusEnum orderStatusEnum) {
		return Arrays.asList(FILLED, CANCELED, REJECTED, EXPIRED).contains(orderStatusEnum);
	}
}
