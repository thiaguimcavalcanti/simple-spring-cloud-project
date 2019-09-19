package com.bot.commons.dto;

import lombok.Data;

import java.util.List;

@Data
public class BaseListDTO<T> {
    private List<T> data;

    private BaseListDTO() {}

    private BaseListDTO(List<T> data) {
        this.data = data;
    }

    public static <T> BaseListDTO<T> of(List<T> data) {
        return new BaseListDTO<>(data);
    }
}
