package com.bot.commons.dto;

import lombok.Data;

@Data
public class BalanceDTO {
    private String currency;
    private double balance;
    private double available;
    private double pending;
}
