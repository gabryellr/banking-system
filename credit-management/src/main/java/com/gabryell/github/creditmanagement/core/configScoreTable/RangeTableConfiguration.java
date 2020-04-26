package com.gabryell.github.creditmanagement.core.configScoreTable;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "management")
public class RangeTableConfiguration {

    private BigDecimal firstRangeCreditCard;
    private BigDecimal firstRangeOverdraft;

    private BigDecimal secondRangeCreditCard;
    private BigDecimal secondRangeOverdraft;

    private BigDecimal thirdRangeCreditCard;
    private BigDecimal thirdRangeOverdraft;

}