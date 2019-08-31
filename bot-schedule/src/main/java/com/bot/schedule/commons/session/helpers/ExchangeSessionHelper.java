package com.bot.schedule.commons.session.helpers;

import com.bot.exchanges.commons.entities.ExchangeProduct;
import com.bot.exchanges.commons.enums.ExchangeEnum;
import com.bot.exchanges.commons.enums.PeriodEnum;
import com.bot.schedule.commons.session.ExchangeSession;
import org.apache.commons.lang3.tuple.Pair;

import javax.servlet.ServletContext;
import java.io.Serializable;
import java.util.List;

public class ExchangeSessionHelper implements Serializable {

	private static volatile ExchangeSessionHelper instance = null;

	private ServletContext servletContext;

	private ExchangeSessionHelper(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public static ExchangeSessionHelper getInstance(ServletContext servletContext) {
		if (instance == null) {
			synchronized (ExchangeSessionHelper.class) {
				if (instance == null) {
					instance = new ExchangeSessionHelper(servletContext);
				}
			}
		}
		return instance;
	}

	private String getSessionKey(ExchangeEnum exchangeEnum) {
		return exchangeEnum.name() + "_EXCHANGE";
	}

	public synchronized ExchangeSession get(ExchangeEnum exchangeEnum) {
		String sessionKey = getSessionKey(exchangeEnum);

		ExchangeSession exchangeSession = (ExchangeSession) servletContext.getAttribute(sessionKey);
		if (exchangeSession == null) {
			exchangeSession = new ExchangeSession(exchangeEnum);
			servletContext.setAttribute(sessionKey, exchangeSession);
		}

		return exchangeSession;
	}

	public synchronized void remove(ExchangeEnum exchangeEnum) {
		servletContext.removeAttribute(getSessionKey(exchangeEnum));
	}

	public synchronized void add(ExchangeEnum exchangeEnum,
			ExchangeSession exchangeSession) {
		servletContext.setAttribute(getSessionKey(exchangeEnum), exchangeSession);
	}

	public void initializeExchangeSession(ExchangeEnum exchangeEnum, List<ExchangeProduct> exchangeProducts) {
		ExchangeSession exchangeSession = get(exchangeEnum);
		exchangeSession.initialize(exchangeProducts);
	}

	public synchronized ExchangeProduct getFirstExchangeProductToRefreshLatestCandlestick(ExchangeEnum exchangeEnum,
																						  PeriodEnum periodEnum) {
		ExchangeSession exchangeSession = get(exchangeEnum);
		return exchangeSession.getExchangeProductsToRefreshLatestCandlestick().getFirstItem(periodEnum, true);
	}

	public synchronized void addExchangeProductsToRefreshLatestCandlestick(ExchangeEnum exchangeEnum,
																		   PeriodEnum periodEnum,
																		   List<ExchangeProduct> exchangeProducts) {
		ExchangeSession exchangeSession = get(exchangeEnum);
		exchangeSession.getExchangeProductsToRefreshLatestCandlestick().putAll(periodEnum, exchangeProducts);
	}

	public synchronized <T extends ExchangeProduct> void addExchangeProductToRefreshLatestCandlestick(ExchangeEnum exchangeEnum,
																									  PeriodEnum periodEnum,
																									  ExchangeProduct exchangeProduct) {
		ExchangeSession exchangeSession = get(exchangeEnum);
		exchangeSession.getExchangeProductsToRefreshLatestCandlestick().put(periodEnum, exchangeProduct);
	}

	public synchronized void addExchangeProductToStrategyAnalysis(ExchangeEnum exchangeEnum,
																  PeriodEnum periodEnum,
																  ExchangeProduct exchangeProducts) {
		ExchangeSession exchangeSession = get(exchangeEnum);
		exchangeSession.getExchangeProductsToStrategyAnalysis().add(Pair.of(periodEnum, exchangeProducts));
	}

	public synchronized Pair<PeriodEnum, ExchangeProduct> getFirstExchangeProductToStrategyAnalysis(ExchangeEnum exchangeEnum) {
		ExchangeSession exchangeSession = get(exchangeEnum);
		return exchangeSession.getExchangeProductsToStrategyAnalysis().getFirstItem(true);
	}

	public synchronized void removeExchangeProductToStrategyAnalysis(ExchangeEnum exchangeEnum,
																	 Pair<PeriodEnum, ExchangeProduct> pair) {
		ExchangeSession exchangeSession = get(exchangeEnum);
		exchangeSession.getExchangeProductsToStrategyAnalysis().remove(pair);
	}
}
