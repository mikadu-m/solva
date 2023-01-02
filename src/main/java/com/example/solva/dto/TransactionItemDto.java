package com.example.solva.dto;

import com.example.solva.entity.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@Getter
public final class TransactionItemDto implements Serializable {
    private Long id;
    private long limitValue;
    private Instant limitDatetime;
    private String limitCurrency;
    private long accountFrom;
    private long accountTo;
    private String currencyShortname;
    private BigDecimal sum;
    private String expenseCategory;
    private boolean limitExceeded;
    public TransactionItemDto(Transaction transaction) {
            this.id = transaction.getId();
            this.accountFrom = transaction.getAccountFrom();
            this.accountTo = transaction.getAccountTo();
            this.limitValue = transaction.getLimitId().getLimitValue();
            this.limitCurrency = transaction.getLimitId().getLimitCurrency();
            this.limitDatetime = transaction.getLimitId().getReceivedTime();
            this.currencyShortname = transaction.getCurrencyShortname();
            this.sum = transaction.getSum();
            this.expenseCategory = transaction.getExpenseCategory();
            this.limitExceeded = transaction.isLimitExceeded();
        }
}
