package com.bot.exchanges.cryptocompare.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BaseCryptoCompareDTO<T> {

    @JsonProperty("Data")
    private List<T> data;
}
