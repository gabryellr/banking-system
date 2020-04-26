package com.gabryell.github.creditmanagement.core.service;

import com.gabryell.github.creditmanagement.core.configScoreTable.RangeTableConfiguration;
import com.gabryell.github.creditmanagement.exceptionHandler.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CreditService {

    private RangeTableConfiguration creditRangeTable;

    public CreditService(RangeTableConfiguration creditRangeTable) {
        this.creditRangeTable = creditRangeTable;
    }

    public Map<String, BigDecimal> createCreditBasedOnScore(int customerScore) {
        Map<String, BigDecimal> customerCredits = new HashMap<>();

        if (customerScore == 0 || customerScore == 1)
            return customerCredits;
        if (customerScore > 9)
            throw new BusinessException("Score [" + customerScore + "] is not on the table scores");

        if (customerScore >= 2 && customerScore <= 5) {
            customerCredits.put("overdraft", creditRangeTable.getFirstRangeOverdraft());
            customerCredits.put("creditCard", creditRangeTable.getFirstRangeCreditCard());
        } else if (customerScore >= 6 && customerScore <= 8) {
            customerCredits.put("overdraft", creditRangeTable.getSecondRangeOverdraft());
            customerCredits.put("creditCard", creditRangeTable.getSecondRangeCreditCard());
        } else {
            customerCredits.put("overdraft", creditRangeTable.getThirdRangeOverdraft());
            customerCredits.put("creditCard", creditRangeTable.getThirdRangeCreditCard());
        }

        return customerCredits;
    }

}


