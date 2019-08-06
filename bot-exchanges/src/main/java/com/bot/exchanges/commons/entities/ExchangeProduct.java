package com.bot.exchanges.commons.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ExchangeProduct")
public class ExchangeProduct extends AbstractLongIDEntity {

    /** Exchange */
    @ToString.Exclude
    @JoinColumn(name = "Exchange_ID", nullable = false, referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Exchange exchange;

    @Column(name = "Exchange_ID", insertable = false, updatable = false)
    private Long exchangeId;

    /** Coin */
    @ToString.Exclude
    @JoinColumn(name = "Product_ID", nullable = false, referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(name = "Product_ID", insertable = false, updatable = false)
    private String productId;
}
