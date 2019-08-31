package com.bot.schedule.commons.session;

import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;

@NoArgsConstructor
public class CustomLinkedList<E> extends ConcurrentLinkedDeque<E> {

	private static final long serialVersionUID = 3891339931308767826L;

	public CustomLinkedList(Collection<? extends E> c) {
		addAll(c);
	}

	public synchronized E getFirstItem(boolean reInsert) {
		// Remove first coin
		E value = pollFirst();

		// Add again first coin in the end
		if (value != null && reInsert) {
			offerLast(value);
		}

		return value;
	}
}
