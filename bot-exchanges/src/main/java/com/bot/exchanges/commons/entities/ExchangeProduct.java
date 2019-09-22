package com.bot.exchanges.commons.entities;

import com.bot.commons.types.CustomBigDecimal;
import com.bot.exchanges.commons.entities.types.CustomBigDecimalType;
import com.bot.exchanges.commons.utils.CommonConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static com.bot.commons.utils.CommonConstants.MIN_PRICE;
import static com.bot.exchanges.commons.utils.CommonConstants.CUSTOMBIGDECIMAL_DATA_TYPE;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ExchangeProduct")
@TypeDef(name = CUSTOMBIGDECIMAL_DATA_TYPE, defaultForType = CustomBigDecimal.class, typeClass = CustomBigDecimalType.class)
public class ExchangeProduct extends AbstractLongIDEntity {

    /** Exchange */
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "Exchange_ID", nullable = false, referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Exchange exchange;

    @Column(name = "Exchange_ID", insertable = false, updatable = false)
    private Long exchangeId;

    /** Product */
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "Product_ID", nullable = false, referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(name = "Product_ID", insertable = false, updatable = false)
    private String productId;

    /** Base Product */
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "Base_Product_ID", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product baseProduct;

    @Column(name = "Base_Product_ID", insertable = false, updatable = false)
    private String baseProductId;

    @Column(name = "Active")
    private boolean active;

    @Column(name = "MinPrice", precision = 14, scale = 8)
    @Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
    private CustomBigDecimal minPrice = MIN_PRICE;

    @Column(name = "MaxPrice", precision = 20, scale = 8)
    @Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
    private CustomBigDecimal maxPrice;

    @Column(name = "TickSize", precision = 14, scale = 8)
    @Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
    private CustomBigDecimal tickSize = MIN_PRICE;

    @Column(name = "MinQty", precision = 14, scale = 8)
    @Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
    private CustomBigDecimal minQty = MIN_PRICE;

    @Column(name = "MaxQty", precision = 20, scale = 8)
    @Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
    private CustomBigDecimal maxQty;

    @Column(name = "MinTotal", precision = 14, scale = 8)
    @Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
    private CustomBigDecimal minTotal = MIN_PRICE;

    @Column(name = "QtySize", precision = 14, scale = 8)
    @Type(type = CUSTOMBIGDECIMAL_DATA_TYPE)
    private CustomBigDecimal qtySize = MIN_PRICE;
}
