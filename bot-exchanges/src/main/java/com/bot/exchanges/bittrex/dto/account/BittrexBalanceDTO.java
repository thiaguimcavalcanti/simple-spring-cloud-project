package com.bot.exchanges.bittrex.dto.account;

import com.bot.exchanges.commons.dto.BalanceDTO;
import com.fasterxml.jackson.annotation.JsonSetter;

public class BittrexBalanceDTO extends BalanceDTO {

    @Override
    @JsonSetter("Currency")
    public void setCurrency(String currency) {
        super.setCurrency(currency);
    }

    @Override
    @JsonSetter("Balance")
    public void setBalance(double balance) {
        super.setBalance(balance);
    }

    @Override
    @JsonSetter("Available")
    public void setAvailable(double available) {
        super.setAvailable(available);
    }

    @Override
    @JsonSetter("Pending")
    public void setPending(double pending) {
        super.setPending(pending);
    }
}
