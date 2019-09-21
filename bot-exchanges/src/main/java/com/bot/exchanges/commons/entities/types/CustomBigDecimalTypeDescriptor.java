package com.bot.exchanges.commons.entities.types;

import com.bot.commons.types.CustomBigDecimal;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CustomBigDecimalTypeDescriptor extends AbstractTypeDescriptor<CustomBigDecimal> {

	static final CustomBigDecimalTypeDescriptor INSTANCE = new CustomBigDecimalTypeDescriptor();

    private CustomBigDecimalTypeDescriptor() {
		super(CustomBigDecimal.class);
	}

	public String toString(CustomBigDecimal value) {
		return value.toString();
	}

	public CustomBigDecimal fromString(String string) {
		return new CustomBigDecimal(string);
	}

	@Override
	public boolean areEqual(CustomBigDecimal one, CustomBigDecimal another) {
		return one == another || (one != null && another != null && one.compareTo(another) == 0);
	}

	@Override
	public int extractHashCode(CustomBigDecimal value) {
		return value.intValue();
	}

	@SuppressWarnings({ "unchecked" })
	public <X> X unwrap(CustomBigDecimal value, Class<X> type, WrapperOptions options) {
		if (value == null) {
			return null;
		}
		if (BigDecimal.class.isAssignableFrom(type)) {
			return (X) value.getDelegate();
		}
		throw unknownUnwrap(type);
	}

	public <X> CustomBigDecimal wrap(X value, WrapperOptions options) {
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
			return new CustomBigDecimal((String) value);
		}
		throw unknownWrap(value.getClass());
	}
}