package com.bot.exchanges.commons.utils;

import com.bot.commons.enums.ExchangeEnum;
import com.bot.commons.enums.PeriodEnum;
import com.bot.commons.types.CustomBigDecimal;
import com.bot.exchanges.commons.dto.CandlestickDTO;
import com.bot.exchanges.commons.entities.Candlestick;
import com.bot.exchanges.commons.entities.ExchangeProduct;

public class CandlestickTransformer {

    public static Candlestick convertCandlestickDTOToEntity(ExchangeEnum exchangeEnum, ExchangeProduct exchangeProduct,
                                                            PeriodEnum periodEnum, CandlestickDTO candlestickDTO) {
        if (candlestickDTO == null) { return null; }

        Candlestick candlestick = new Candlestick();
        candlestick.setOpenPrice(CustomBigDecimal.valueOf(candlestickDTO.getOpenPrice()));
        candlestick.setClosePrice(CustomBigDecimal.valueOf(candlestickDTO.getClosePrice()));
        candlestick.setVolume(CustomBigDecimal.valueOf(candlestickDTO.getVolume()));
        candlestick.setMaxPrice(CustomBigDecimal.valueOf(candlestickDTO.getMaxPrice()));
        candlestick.setMinPrice(CustomBigDecimal.valueOf(candlestickDTO.getMinPrice()));
        candlestick.setEndTime(exchangeEnum.getParseDateFunction().apply(candlestickDTO.getEndTime(), periodEnum.getDuration()));
        candlestick.setBeginTime(candlestick.getEndTime().minus(periodEnum.getDuration()));
        candlestick.setExchangeProduct(exchangeProduct);
        candlestick.setPeriodEnum(periodEnum);
        candlestick.setId(candlestick.toString());
        return candlestick;
    }


    public static com.bot.commons.dto.CandlestickDTO convertCandlestickDTOToCommonDTO(ExchangeEnum exchangeEnum,
                                                                                      ExchangeProduct exchangeProduct,
                                                                                      PeriodEnum periodEnum,
                                                                                      CandlestickDTO candlestickDTO) {
        if (candlestickDTO == null) { return null; }

        com.bot.commons.dto.CandlestickDTO commonCandlestickDTO = new com.bot.commons.dto.CandlestickDTO();
        commonCandlestickDTO.setOpenPrice(CustomBigDecimal.valueOf(candlestickDTO.getOpenPrice()));
        commonCandlestickDTO.setClosePrice(CustomBigDecimal.valueOf(candlestickDTO.getClosePrice()));
        commonCandlestickDTO.setVolume(CustomBigDecimal.valueOf(candlestickDTO.getVolume()));
        commonCandlestickDTO.setMaxPrice(CustomBigDecimal.valueOf(candlestickDTO.getMaxPrice()));
        commonCandlestickDTO.setMinPrice(CustomBigDecimal.valueOf(candlestickDTO.getMinPrice()));
        commonCandlestickDTO.setEndTime(exchangeEnum.getParseDateFunction().apply(candlestickDTO.getEndTime(), periodEnum.getDuration()));
        commonCandlestickDTO.setBeginTime(commonCandlestickDTO.getEndTime().minus(periodEnum.getDuration()));
        commonCandlestickDTO.setPeriodEnum(periodEnum);
        commonCandlestickDTO.setId(commonCandlestickDTO.toString());
        return commonCandlestickDTO;
    }
}
