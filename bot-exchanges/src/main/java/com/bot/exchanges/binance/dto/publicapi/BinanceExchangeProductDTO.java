package com.bot.exchanges.binance.dto.publicapi;

import com.bot.commons.dto.ExchangeProductDTO;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class BinanceExchangeProductDTO extends ExchangeProductDTO {

    @Override
    @JsonSetter("baseAsset")
    public void setProductId(String productId) { super.setProductId(productId); }

    @Override
    @JsonSetter("quoteAsset")
    public void setBaseProductId(String baseProductId) { super.setBaseProductId(baseProductId); }

    public void setStatus(String status) { super.setActive("TRADING".equals(status)); }

    public void setFilters(List<BinanceExchangeProductFilterDTO> filters) {
        if (CollectionUtils.isNotEmpty(filters)) {
            filters.forEach(filter -> {
                switch (filter.getFilterType()) {
                    case PRICE_FILTER:
                        setMinPrice(filter.getMinPrice());
                        setMaxPrice(filter.getMaxPrice());
                        setTickSize(filter.getTickSize());
                        break;
                    case LOT_SIZE:
                        setMinQty(filter.getMinQty());
                        setMaxQty(filter.getMaxQty());
                        setQtySize(filter.getStepSize());
                        break;
                    case MIN_NOTIONAL:
                        setMinTotal(filter.getMinNotional());
                        break;
                    default:
                        break;
                }
            });
        }
    }
}
