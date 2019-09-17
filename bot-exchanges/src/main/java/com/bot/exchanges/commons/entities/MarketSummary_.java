package com.bot.exchanges.commons.entities;

import java.time.ZonedDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ta4j.core.num.Num;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor", date = "2019-09-16T22:22:05.160-0300")
@StaticMetamodel(MarketSummary.class)
public abstract class MarketSummary_ extends com.bot.exchanges.commons.entities.AbstractLongIDEntity_ {

	public static volatile SingularAttribute<MarketSummary, Num> volume;
	public static volatile SingularAttribute<MarketSummary, ZonedDateTime> tickDate;
	public static volatile SingularAttribute<MarketSummary, Num> prevDay;
	public static volatile SingularAttribute<MarketSummary, Num> high;
	public static volatile SingularAttribute<MarketSummary, Num> last;
	public static volatile SingularAttribute<MarketSummary, Integer> openBuyOrders;
	public static volatile SingularAttribute<MarketSummary, Num> low;
	public static volatile SingularAttribute<MarketSummary, ExchangeProduct> exchangeProduct;
	public static volatile SingularAttribute<MarketSummary, Double> changePercent;
	public static volatile SingularAttribute<MarketSummary, Num> ask;
	public static volatile SingularAttribute<MarketSummary, Num> bid;
	public static volatile SingularAttribute<MarketSummary, Integer> openSellOrders;

	public static final String VOLUME = "volume";
	public static final String TICK_DATE = "tickDate";
	public static final String PREV_DAY = "prevDay";
	public static final String HIGH = "high";
	public static final String LAST = "last";
	public static final String OPEN_BUY_ORDERS = "openBuyOrders";
	public static final String LOW = "low";
	public static final String EXCHANGE_PRODUCT = "exchangeProduct";
	public static final String CHANGE_PERCENT = "changePercent";
	public static final String ASK = "ask";
	public static final String BID = "bid";
	public static final String OPEN_SELL_ORDERS = "openSellOrders";

}

