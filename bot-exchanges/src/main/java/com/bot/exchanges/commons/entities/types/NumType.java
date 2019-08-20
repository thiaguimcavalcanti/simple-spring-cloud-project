package com.bot.exchanges.commons.entities.types;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.descriptor.sql.NumericTypeDescriptor;
import org.ta4j.core.num.Num;

public class NumType extends AbstractSingleColumnStandardBasicType<Num> {

	public static final BigDecimalType INSTANCE = new BigDecimalType();

	public NumType() {
		super(NumericTypeDescriptor.INSTANCE, NumTypeDescriptor.INSTANCE);
	}

	@Override
	public String getName() {
		return "num_type";
	}

	@Override
	protected boolean registerUnderJavaType() {
		return true;
	}
}