package com.bot.schedule.commons.session;

import com.bot.exchanges.commons.enums.PeriodEnum;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class PeriodEnumMapSession<T> {

	private Map<PeriodEnum, CustomLinkedList<T>> mapByPeriod;

	public Map<PeriodEnum, CustomLinkedList<T>> getAll() {
		if (mapByPeriod == null) {
			mapByPeriod = Collections.synchronizedMap(new EnumMap<>(PeriodEnum.class));
		}
		return mapByPeriod;
	}

	public void putAll(PeriodEnum periodEnum, List<T> value) {
		getAll().put(periodEnum, new CustomLinkedList<>(value));
	}

	public void clearAll() {
		this.mapByPeriod.clear();
	}

	public CustomLinkedList<T> getByPeriod(PeriodEnum periodEnum) {
		return getAll().putIfAbsent(periodEnum, new CustomLinkedList<>());
	}

	public synchronized void put(PeriodEnum periodEnum, T value) {
		CustomLinkedList<T> customLinkedList = getByPeriod(periodEnum);

		if (customLinkedList != null && value != null && !customLinkedList.contains(value)) {
			customLinkedList.offerLast(value);
		}
	}

	public synchronized boolean remove(PeriodEnum periodEnum, T value) {
		CustomLinkedList<T> customLinkedList = getByPeriod(periodEnum);
		return customLinkedList != null && value != null ? customLinkedList.remove(value) : null;
	}

	public synchronized T getFirstItem(PeriodEnum periodEnum, boolean reInsert) {
		CustomLinkedList<T> customLinkedList = getByPeriod(periodEnum);
		return customLinkedList != null ? customLinkedList.getFirstItem(reInsert) : null;
	}
}
