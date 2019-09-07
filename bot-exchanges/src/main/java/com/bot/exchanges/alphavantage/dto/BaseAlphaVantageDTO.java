package com.bot.exchanges.alphavantage.dto;

import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public abstract class BaseAlphaVantageDTO<T> {

    private List<T> items = new ArrayList<>();

    public void addItem(T item) {
        items.add(item);
    }

    public abstract T newInstance(Map<String, String> properties);

    public abstract DateTimeFormatter getDateTimeFormatter();
}
