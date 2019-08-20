package com.bot.exchanges.commons.entities.types;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.ta4j.core.num.Num;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumTypeDescriptor extends AbstractTypeDescriptor<Num> {

	public static final NumTypeDescriptor INSTANCE = new NumTypeDescriptor();

	public NumTypeDescriptor() {
		super(Num.class);
	}

	public String toString(Num value) {
		return value.toString();
	}

	public Num fromString(String string) {
		return CustomBigDecimal.valueOf(string);
	}

	@Override
	public boolean areEqual(Num one, Num another) {
		return one == another || (one != null && another != null && one.compareTo(another) == 0);
	}

	@Override
	public int extractHashCode(Num value) {
		return value.intValue();
	}

	@SuppressWarnings({ "unchecked" })
	public <X> X unwrap(Num value, Class<X> type, WrapperOptions options) {
		if (value == null) {
			return null;
		}
		if (BigDecimal.class.isAssignableFrom(type)) {
			return (X) value.getDelegate();
		}
		throw unknownUnwrap(type);
	}

	public <X> Num wrap(X value, WrapperOptions options) {
		if (value == null) {
			return null;
		}
		if (BigDecimal.class.isInstance(value)) {
			return CustomBigDecimal.valueOf((BigDecimal) value);
		}
		if (BigInteger.class.isInstance(value)) {
			return CustomBigDecimal.valueOf((BigInteger) value);
		}
		if (Number.class.isInstance(value)) {
			return CustomBigDecimal.valueOf((BigInteger) value);
		}
		if (String.class.isInstance(value)) {
			return CustomBigDecimal.valueOf((String) value);
		}
		throw unknownWrap(value.getClass());
	}
}