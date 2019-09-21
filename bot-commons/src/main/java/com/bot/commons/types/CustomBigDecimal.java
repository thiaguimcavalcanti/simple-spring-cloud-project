package com.bot.commons.types;

import com.bot.commons.utils.CustomBigDecimalDeserializer;
import com.bot.commons.utils.CustomBigDecimalSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.ta4j.core.num.Num;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

@JsonDeserialize(using = CustomBigDecimalDeserializer.class)
@JsonSerialize(using = CustomBigDecimalSerializer.class)
public class CustomBigDecimal implements Num {

	private static final int DEFAULT_PRECISION = 32;

	private final MathContext mathContext;

	private final BigDecimal delegate;

	@Override
	public Function<Number, Num> function() {
		return (number -> CustomBigDecimal.valueOf(number.toString(), mathContext.getPrecision()));
	}

	/**
	 * Constructor.
	 * 
	 * @param val the string representation of the Num value
	 */
	public CustomBigDecimal(String val) {
		delegate = new BigDecimal(val);
		int precision = Math.max(delegate.precision(), DEFAULT_PRECISION);
		mathContext = new MathContext(precision, RoundingMode.HALF_UP);
	}

	/**
	 * Constructor. Above double precision, only String parameters can represent the
	 * value.
	 *
	 * @param val       the string representation of the Num value
	 * @param precision the int precision of the Num value
	 */
	public CustomBigDecimal(String val, int precision) {
		mathContext = new MathContext(precision, RoundingMode.HALF_UP);
		delegate = new BigDecimal(val, new MathContext(precision, RoundingMode.HALF_UP));
	}

	private CustomBigDecimal(short val) {
		mathContext = new MathContext(DEFAULT_PRECISION, RoundingMode.HALF_UP);
		delegate = new BigDecimal(val, mathContext);
	}

	private CustomBigDecimal(int val) {
		mathContext = new MathContext(DEFAULT_PRECISION, RoundingMode.HALF_UP);
		delegate = BigDecimal.valueOf(val);
	}

	private CustomBigDecimal(long val) {
		mathContext = new MathContext(DEFAULT_PRECISION, RoundingMode.HALF_UP);
		delegate = BigDecimal.valueOf(val);
	}

	private CustomBigDecimal(float val) {
		mathContext = new MathContext(DEFAULT_PRECISION, RoundingMode.HALF_UP);
		delegate = new BigDecimal(val, mathContext);
	}

	private CustomBigDecimal(double val) {
		mathContext = new MathContext(DEFAULT_PRECISION, RoundingMode.HALF_UP);
		delegate = BigDecimal.valueOf(val);
	}

	private CustomBigDecimal(BigDecimal val, int precision) {
		mathContext = new MathContext(precision, RoundingMode.HALF_UP);
		delegate = Objects.requireNonNull(val);
	}

	/**
	 * Returns the underlying {@link BigDecimal} delegate
	 * 
	 * @return BigDecimal delegate instance of this instance
	 */
	@Override
	public Number getDelegate() {
		return delegate;
	}

	/**
	 * Returns the underlying {@link MathContext} mathContext
	 * 
	 * @return MathContext of this instance
	 */
	public MathContext getMathContext() {
		return mathContext;
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public CustomBigDecimal plus(Num augend) {
		if (augend == null || augend.isNaN()) {
			throw new IllegalArgumentException("This parameter is required!");
		}
		BigDecimal bigDecimal = ((CustomBigDecimal) augend).delegate;
		int precision = mathContext.getPrecision();
		BigDecimal result = delegate.add(bigDecimal, mathContext);
		return new CustomBigDecimal(result, precision);
	}

	/**
	 * Returns a {@code Num} whose value is {@code (this - augend)}, with rounding
	 * according to the context settings.
	 * 
	 * @param subtrahend value to be subtracted from this {@code Num}.
	 * @return {@code this - subtrahend}, rounded as necessary
	 * @see BigDecimal#subtract(BigDecimal, MathContext)
	 */
	@Override
	public CustomBigDecimal minus(Num subtrahend) {
		if (subtrahend == null || subtrahend.isNaN()) {
			throw new IllegalArgumentException("This parameter is required!");
		}
		BigDecimal bigDecimal = ((CustomBigDecimal) subtrahend).delegate;
		int precision = mathContext.getPrecision();
		BigDecimal result = delegate.subtract(bigDecimal, mathContext);
		return new CustomBigDecimal(result, precision);
	}

	/**
	 * Returns a {@code Num} whose value is {@code this * multiplicand}, with
	 * rounding according to the context settings.
	 * 
	 * @param multiplicand value to be multiplied by this {@code Num}.
	 * @return {@code this * multiplicand}, rounded as necessary
	 * @see BigDecimal#multiply(BigDecimal, MathContext)
	 */
	@Override
	public CustomBigDecimal multipliedBy(Num multiplicand) {
		if (multiplicand == null || multiplicand.isNaN()) {
			throw new IllegalArgumentException("This parameter is required!");
		}
		BigDecimal bigDecimal = ((CustomBigDecimal) multiplicand).delegate;
		int precision = mathContext.getPrecision();
		BigDecimal result = delegate.multiply(bigDecimal, new MathContext(precision, RoundingMode.HALF_UP));
		return new CustomBigDecimal(result, precision);
	}

	/**
	 * Returns a {@code Num} whose value is {@code (this / divisor)}, with rounding
	 * according to the context settings.
	 * 
	 * @param divisor value by which this {@code Num} is to be divided.
	 * @return {@code this / divisor}, rounded as necessary
	 * @see BigDecimal#divide(BigDecimal, MathContext)
	 */
	@Override
	public CustomBigDecimal dividedBy(Num divisor) {
		if (divisor == null || divisor.isNaN() || divisor.isZero()) {
			throw new IllegalArgumentException("This parameter is required!");
		}
		BigDecimal bigDecimal = ((CustomBigDecimal) divisor).delegate;
		int precision = mathContext.getPrecision();
		BigDecimal result = delegate.divide(bigDecimal, new MathContext(precision, RoundingMode.HALF_UP));
		return new CustomBigDecimal(result, precision);
	}

	/**
	 * Returns a {@code Num} whose value is {@code (this % divisor)}, with rounding
	 * according to the context settings.
	 * 
	 * @param divisor value by which this {@code Num} is to be divided.
	 * @return {@code this % divisor}, rounded as necessary.
	 * @see BigDecimal#remainder(BigDecimal, MathContext)
	 */
	@Override
	public CustomBigDecimal remainder(Num divisor) {
		BigDecimal bigDecimal = ((CustomBigDecimal) divisor).delegate;
		int precision = mathContext.getPrecision();
		BigDecimal result = delegate.remainder(bigDecimal, new MathContext(precision, RoundingMode.HALF_UP));
		return new CustomBigDecimal(result, precision);
	}

	/**
	 * Returns a {@code Num} whose value is rounded down to the nearest whole
	 * number.
	 * 
	 * @return <tt>this<sup>n</sup></tt>
	 */
	public CustomBigDecimal floor() {
		int precision = Math.max(mathContext.getPrecision(), DEFAULT_PRECISION);
		return new CustomBigDecimal(delegate.setScale(0, RoundingMode.FLOOR), precision);
	}

	/**
	 * Returns a {@code Num} whose value is rounded up to the nearest whole number.
	 * 
	 * @return <tt>this<sup>n</sup></tt>
	 */
	public CustomBigDecimal ceil() {
		int precision = Math.max(mathContext.getPrecision(), DEFAULT_PRECISION);
		return new CustomBigDecimal(delegate.setScale(0, RoundingMode.CEILING), precision);
	}

	/**
	 * Returns a {@code Num} whose value is <tt>(this<sup>n</sup>)</tt>.
	 * 
	 * @param n power to raise this {@code Num} to.
	 * @return <tt>this<sup>n</sup></tt>
	 * @see BigDecimal#pow(int, MathContext)
	 */
	@Override
	public CustomBigDecimal pow(int n) {
		int precision = mathContext.getPrecision();
		BigDecimal result = delegate.pow(n, new MathContext(precision, RoundingMode.HALF_UP));
		return new CustomBigDecimal(result, precision);
	}

	/**
	 * Returns the correctly rounded positive square root of this {@code Num}. /!\
	 * Warning! Uses DEFAULT_PRECISION.
	 * 
	 * @return the positive square root of {@code this}
	 * @see CustomBigDecimal#sqrt(int)
	 */
	public CustomBigDecimal sqrt() {
		return sqrt(DEFAULT_PRECISION);
	}

	/**
	 * Returns a {@code num} whose value is <tt>√(this)</tt>.
	 * 
	 * @param precision to calculate.
	 * @return <tt>this<sup>n</sup></tt>
	 */
	@Override
	public CustomBigDecimal sqrt(int precision) {
		int comparedToZero = delegate.compareTo(BigDecimal.ZERO);
		switch (comparedToZero) {
		case -1:
			return null;
		case 0:
			return CustomBigDecimal.valueOf(0);
		}

		// Direct implementation of the example in:
		// https://en.wikipedia.org/wiki/Methods_of_computing_square_roots#Babylonian_method
		MathContext precisionContext = new MathContext(precision, RoundingMode.HALF_UP);
		BigDecimal estimate = new BigDecimal(delegate.toString(), precisionContext);
		String string = String.format(Locale.ROOT, "%1.1e", estimate);
		if (string.contains("e")) {
			String[] parts = string.split("e");
			BigDecimal mantissa = new BigDecimal(parts[0]);
			BigDecimal exponent = new BigDecimal(parts[1]);
			if (exponent.remainder(new BigDecimal(2)).compareTo(BigDecimal.ZERO) > 0) {
				exponent = exponent.subtract(BigDecimal.ONE);
				mantissa = mantissa.multiply(BigDecimal.TEN);
			}
			BigDecimal estimatedMantissa = mantissa.compareTo(BigDecimal.TEN) < 0 ? new BigDecimal(2)
					: new BigDecimal(6);
			BigDecimal estimatedExponent = exponent.divide(new BigDecimal(2));
			String estimateString = String.format("%sE%s", estimatedMantissa, estimatedExponent);
			DecimalFormat format = new DecimalFormat();
			format.setParseBigDecimal(true);
			try {
				estimate = (BigDecimal) format.parse(estimateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		BigDecimal delta;
		BigDecimal test;
		BigDecimal sum;
		BigDecimal newEstimate;
		BigDecimal two = new BigDecimal(2);
		do {
			test = delegate.divide(estimate, precisionContext);
			sum = estimate.add(test);
			newEstimate = sum.divide(two, precisionContext);
			delta = newEstimate.subtract(estimate).abs();
			estimate = newEstimate;
		} while (delta.compareTo(BigDecimal.ZERO) > 0);
		return CustomBigDecimal.valueOf(estimate, precision);
	}

	/**
	 * Returns a {@code Num} whose value is the absolute value of this {@code Num}.
	 * 
	 * @return {@code abs(this)}
	 */
	@Override
	public CustomBigDecimal abs() {
		return new CustomBigDecimal(delegate.abs(), mathContext.getPrecision());
	}

	/**
	 * Checks if the value is zero.
	 * 
	 * @return true if the value is zero, false otherwise
	 */
	@Override
	public boolean isZero() {
		return compareTo(CustomBigDecimal.valueOf(0)) == 0;
	}

	/**
	 * Checks if the value is greater than zero.
	 * 
	 * @return true if the value is greater than zero, false otherwise
	 */
	@Override
	public boolean isPositive() {
		return compareTo(CustomBigDecimal.valueOf(0)) > 0;
	}

	/**
	 * Checks if the value is zero or greater.
	 * 
	 * @return true if the value is zero or greater, false otherwise
	 */
	@Override
	public boolean isPositiveOrZero() {
		return compareTo(CustomBigDecimal.valueOf(0)) >= 0;
	}

	/**
	 * Checks if the value is less than zero.
	 * 
	 * @return true if the value is less than zero, false otherwise
	 */
	@Override
	public boolean isNegative() {
		return compareTo(function().apply(0)) < 0;
	}

	/**
	 * Checks if the value is zero or less.
	 * 
	 * @return true if the value is zero or less, false otherwise
	 */
	@Override
	public boolean isNegativeOrZero() {
		return compareTo(CustomBigDecimal.valueOf(0)) <= 0;
	}

	/**
	 * Checks if this value is equal to another.
	 * 
	 * @param other the other value, not null
	 * @return true is this is greater than the specified value, false otherwise
	 */
	@Override
	public boolean isEqual(Num other) {
		return !other.isNaN() && compareTo(other) == 0;
	}

	/**
	 * Checks if this value matches another to a precision.
	 *
	 * @param other     the other value, not null
	 * @param precision the int precision
	 * @return true is this matches the specified value to a precision, false
	 *         otherwise
	 */
	public boolean matches(Num other, int precision) {
		Num otherNum = CustomBigDecimal.valueOf(other.toString(), precision);
		Num thisNum = CustomBigDecimal.valueOf(this.toString(), precision);
		return thisNum.toString().equals(otherNum.toString());
	}

	/**
	 * Checks if this value matches another within an offset.
	 *
	 * @param other the other value, not null
	 * @param delta the {@link Num} offset
	 * @return true is this matches the specified value within an offset, false
	 *         otherwise
	 */
	public boolean matches(Num other, Num delta) {
		Num result = this.minus(other);
		return !result.isGreaterThan(delta);
	}

	/**
	 * Checks if this value is greater than another.
	 * 
	 * @param other the other value, not null
	 * @return true is this is greater than the specified value, false otherwise
	 */
	@Override
	public boolean isGreaterThan(Num other) {
		return !other.isNaN() && compareTo(other) > 0;
	}

	/**
	 * Checks if this value is greater than or equal to another.
	 * 
	 * @param other the other value, not null
	 * @return true is this is greater than or equal to the specified value, false
	 *         otherwise
	 */
	@Override
	public boolean isGreaterThanOrEqual(Num other) {
		return !other.isNaN() && compareTo(other) > -1;
	}

	/**
	 * Checks if this value is less than another.
	 * 
	 * @param other the other value, not null
	 * @return true is this is less than the specified value, false otherwise
	 */
	@Override
	public boolean isLessThan(Num other) {
		return !other.isNaN() && compareTo(other) < 0;
	}

	@Override
	public boolean isLessThanOrEqual(Num other) {
		return !other.isNaN() && delegate.compareTo(((CustomBigDecimal) other).delegate) < 1;
	}

	@Override
	public int compareTo(Num other) {
		return other.isNaN() ? 0 : delegate.compareTo(((CustomBigDecimal) other).delegate);
	}

	/**
	 * Returns the minimum of this {@code Num} and {@code other}.
	 * 
	 * @param other value with which the minimum is to be computed
	 * @return the {@code Num} whose value is the lesser of this {@code Num} and
	 *         {@code other}. If they are equal, as defined by the
	 *         {@link #compareTo(Num) compareTo} method, {@code this} is returned.
	 */
	@Override
	public CustomBigDecimal min(Num other) {
		if (other == null || other.isNaN()) {
			throw new IllegalArgumentException("This parameter is required!");
		}
		return compareTo(other) <= 0 ? this : (CustomBigDecimal) other;
	}

	/**
	 * Returns the maximum of this {@code Num} and {@code other}.
	 * 
	 * @param other value with which the maximum is to be computed
	 * @return the {@code Num} whose value is the greater of this {@code Num} and
	 *         {@code other}. If they are equal, as defined by the
	 *         {@link #compareTo(Num) compareTo} method, {@code this} is returned.
	 */
	@Override
	public CustomBigDecimal max(Num other) {
		if (other == null || other.isNaN()) {
			throw new IllegalArgumentException("This parameter is required!");
		}
		return compareTo(other) >= 0 ? this : (CustomBigDecimal) other;
	}

	@Override
	public int hashCode() {
		return Objects.hash(delegate);
	}

	/**
	 * {@inheritDoc} Warning: This method returns true if `this` and `obj` are both
	 * NaN.NaN.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Num)) {
			return false;
		}
		return !((Num) obj).isNaN() && this.delegate.compareTo(((CustomBigDecimal) obj).delegate) == 0;
	}

	/**
	 * Returns a {@code Num} version of the given {@code String}.
	 * 
	 * @param val the number
	 * @return the {@code Num}
	 */
	public static CustomBigDecimal valueOf(String val) {
		return new CustomBigDecimal(val);
	}

	/**
	 * Returns a {@code Num) version of the given {@code String} with a
	 * precision. @param val the number
	 * 
	 * @param precision the precision
	 * 
	 * @return the {@code Num}
	 */
	public static CustomBigDecimal valueOf(String val, int precision) {
		return new CustomBigDecimal(val, precision);
	}

	/**
	 * Returns a {@code Num} version of the given {@code short}.
	 * 
	 * @param val the number
	 * @return the {@code Num}
	 */
	public static CustomBigDecimal valueOf(short val) {
		return new CustomBigDecimal(val);
	}

	/**
	 * Returns a {@code Num} version of the given {@code int}.
	 * 
	 * @param val the number
	 * @return the {@code Num}
	 */
	public static CustomBigDecimal valueOf(int val) {
		return new CustomBigDecimal(val);
	}

	/**
	 * Returns a {@code Num} version of the given {@code long}.
	 * 
	 * @param val the number
	 * @return the {@code Num}
	 */
	public static CustomBigDecimal valueOf(long val) {
		return new CustomBigDecimal(val);
	}

	/**
	 * Returns a {@code Num} version of the given {@code float}.
	 * 
	 * @param val the number
	 * @return the {@code Num}
	 */
	public static CustomBigDecimal valueOf(float val) {
		return new CustomBigDecimal(val);
	}

	public static CustomBigDecimal valueOf(BigDecimal val) {
		return new CustomBigDecimal(val, val.precision());
	}

	public static CustomBigDecimal valueOf(BigDecimal val, int precision) {
		return new CustomBigDecimal(val, precision);
	}

	/**
	 * Returns a {@code Num} version of the given {@code double}.
	 * 
	 * @param val the number
	 * @return the {@code Num}
	 */
	public static CustomBigDecimal valueOf(double val) {
		return new CustomBigDecimal(val);
	}

	/**
	 * Returns a {@code Num} version of the given {@code Num}.
	 * 
	 * @param val the number
	 * @return the {@code Num}
	 */
	public static CustomBigDecimal valueOf(CustomBigDecimal val) {
		return val;
	}

	/**
	 * Returns a {@code Num} version of the given {@code Number}. Warning: This
	 * method turns the number into a string first
	 * 
	 * @param val the number
	 * @return the {@code Num}
	 */
	public static CustomBigDecimal valueOf(Number val) {
		return new CustomBigDecimal(val.toString());
	}

	@Override
	public String toString() {
		return delegate.toPlainString();
	}

	@Override
	public CustomBigDecimal pow(Num n) {
		// There is no BigDecimal.pow(BigDecimal). We could do:
		// double Math.pow(double delegate.doubleValue(), double n)
		// But that could overflow any of the three doubles.
		// Instead perform:
		// x^(a+b) = x^a * x^b
		// Where:
		// n = a+b
		// a is a whole number (make sure it doesn't overflow int)
		// remainder 0 <= b < 1
		// So:
		// x^a uses CustomBigDecimal ((CustomBigDecimal) x).pow(int a) cannot overflow
		// Num
		// x^b uses double Math.pow(double x, double b) cannot overflow double because b
		// < 1.
		// As suggested: https://stackoverflow.com/a/3590314

		// get n = a+b, same precision as n
		BigDecimal aplusb = (((CustomBigDecimal) n).delegate);
		// get the remainder 0 <= b < 1, looses precision as double
		BigDecimal b = aplusb.remainder(BigDecimal.ONE);
		// bDouble looses precision
		double bDouble = b.doubleValue();
		// get the whole number a
		BigDecimal a = aplusb.subtract(b);
		// convert a to an int, fails on overflow
		int aInt = a.intValueExact();
		// use BigDecimal pow(int)
		BigDecimal xpowa = delegate.pow(aInt);
		// use double pow(double, double)
		double xpowb = Math.pow(delegate.doubleValue(), bDouble);
		// use CustomBigDecimal.multiply(CustomBigDecimal)
		BigDecimal result = xpowa.multiply(new BigDecimal(xpowb));
		return new CustomBigDecimal(result.toString());
	}
}
