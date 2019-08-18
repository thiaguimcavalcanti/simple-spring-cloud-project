package com.bot.exchanges.commons.entities;

import java.io.Serializable;

public abstract class AbstractEntity<T> implements Serializable {
	public abstract T getId();
	public abstract void setId(T id);
}
