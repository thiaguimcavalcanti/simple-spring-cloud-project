package com.bot.exchanges.commons.entities.types;

import com.bot.commons.types.CustomBigDecimal;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.ta4j.core.num.Num;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumTypeDescriptor extends AbstractTypeDescriptor<Num> {

	static final NumTypeDescriptor INSTANCE = new NumTypeDescriptor();

    private NumTypeDescriptor() {
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
		if (value instanceof BigDecimal) {
			return CustomBigDecimal.valueOf((BigDecimal) value);
		}
		if (value instanceof BigInteger) {
			return CustomBigDecimal.valueOf((BigInteger) value);
		}
		if (value instanceof Number) {
			return CustomBigDecimal.valueOf((Number) value);
		}
		if (value instanceof String) {
			return CustomBigDecimal.valueOf((String) value);
		}
		throw unknownWrap(value.getClass());
	}
}