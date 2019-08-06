package com.bot.gateway.entities;

import java.io.Serializable;

abstract class AbstractEntity<T> implements Serializable {

	private static final long serialVersionUID = -5847031794654130713L;

	public abstract T getId();

	public abstract void setId(T id);
}
