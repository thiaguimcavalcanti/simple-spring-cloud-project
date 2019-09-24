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

    // MACD

    @Column(name = "MacdCrossedUpEma")
    private Boolean macdCrossedUpEma;

    @Column(name = "MacdCrossedDownEma")
    private Boolean macdCrossedDownEma;

    @Column(name = "MacdUnderEma")
    private Boolean macdUnderEma;

    @Column(name = "MacdOverEma")
    private Boolean macdOverEma;

    @Column(name = "MacdDifferenceWidening")
    private Boolean macdDifferenceWidening;

    @Column(name = "MacdAngleWidening")
    private Boolean macdAngleWidening;

    @Column(name = "MacdRising")
    private Boolean macdRising;

    @Column(name = "MacdFalling")
    private Boolean macdFalling;

    // RSI

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

    @Column(name = "RsiDifferenceWidening")
    private Boolean rsiDifferenceWidening;

    @Column(name = "RsiAngleWidening")
    private Boolean rsiAngleWidening;

    @Column(name = "RsiRising")
    private Boolean rsiRising;

    @Column(name = "RsiFalling")
    private Boolean rsiFalling;

    // CCI

    @Column(name = "CciCrossedDownMinus100")
    private Boolean cciCrossedDownMinus100;

    @Column(name = "CciCrossedUp100")
    private Boolean cciCrossedUp100;

    @Column(name = "CciUnderMinus100")
    private Boolean cciUnderMinus100;

    @Column(name = "CciOver100")
    private Boolean cciOver100;

    @Column(name = "CciBetweenMinus100And100")
    private Boolean cciBetweenMinus100And100;

    @Column(name = "CciDifferenceWidening")
    private Boolean cciDifferenceWidening;

    @Column(name = "CciAngleWidening")
    private Boolean cciAngleWidening;

    @Column(name = "CciRising")
    private Boolean cciRising;

    @Column(name = "CciFalling")
    private Boolean cciFalling;
}
