package com.bot.exchanges.commons.entities.types;

import com.bot.commons.types.CustomBigDecimal;
import com.bot.exchanges.commons.utils.CommonConstants;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.descriptor.sql.NumericTypeDescriptor;

import static com.bot.exchanges.commons.utils.CommonConstants.CUSTOMBIGDECIMAL_DATA_TYPE;

public class CustomBigDecimalType extends AbstractSingleColumnStandardBasicType<CustomBigDecimal> {

	public static final BigDecimalType INSTANCE = new BigDecimalType();

	public CustomBigDecimalType() {
		super(NumericTypeDescriptor.INSTANCE, CustomBigDecimalTypeDescriptor.INSTANCE);
	}

	@Override
	public String getName() {
		return CUSTOMBIGDECIMAL_DATA_TYPE;
	}

	@Override
	protected boolean registerUnderJavaType() {
		return true;
	}
}