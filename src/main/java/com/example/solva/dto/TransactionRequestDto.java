package com.example.solva.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public final class TransactionRequestDto implements Serializable {
    private final long accountFrom;
    private final long accountTo;
    private final String currencyShortname;
    private final BigDecimal sum;
    private final String expenseCategory;
}
