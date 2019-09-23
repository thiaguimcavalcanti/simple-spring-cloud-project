package com.bot.exchanges.commons.entities;

import com.bot.commons.enums.PeriodEnum;
import com.bot.commons.types.CustomBigDecimal;
import com.bot.exchanges.commons.entities.types.CustomBigDecimalType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.ZonedDateTime;

import static com.bot.exchanges.commons.utils.CommonConstants.CUSTOMBIGDECIMAL_DATA_TYPE;

@Getter
@Setter
@Entity
@Table(name = "BackTesting", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "ExchangeProduct_ID", "TickDate", "TimePeriod" }) })
@TypeDef(name = CUSTOMBIGDECIMAL_DATA_TYPE, defaultForType = CustomBigDecimal.class, typeClass = CustomBigDecimalType.class)
public class BackTesting extends AbstractLongIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ExchangeProduct_ID", referencedColumnName = "id")
    private ExchangeProduct exchangeProduct;

    @Column(name = "ExchangeProduct_ID", insertable = false, updatable = false)
    private Long exchangeProductId;

    @Column(name = "TickDate")
    private ZonedDateTime tickDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "TimePeriod")
    private PeriodEnum periodEnum;

    @Column(name = "Buy")
    private Boolean buy;

    @Column(name = "Profit", precision = 7, scale = 2)
    @Type(type =  CUSTOMBIGDECIMAL_DATA_TYPE)
    private CustomBigDecimal profit = CustomBigDecimal.valueOf(0);

    @Column(name = "MacdCrossedUpEma")
    private Boolean macdCrossedUpEma;

    @Column(name = "MacdCrossedDownEma")
    private Boolean macdCrossedDownEma;

    @Column(name = "MacdUnderEma")
    private Boolean macdUnderEma;

    @Column(name = "MacdOverEma")
    private Boolean macdOverEma;

    @Column(name = "RsiCrossedDown30")
    private Boolean rsiCrossedDown30;

    @Column(name = "RsiCrossedUp70")
    private Boolean rsiCrossedUp70;

    @Column(name = "RsiUnder30")
    private Boolean rsiUnder30;

    @Column(name = "RsiOver70")
    private Boolean rsiOver70;

    @Column(name = "RsiBetween3070")
    private Boolean rsiBetween3070;
}
