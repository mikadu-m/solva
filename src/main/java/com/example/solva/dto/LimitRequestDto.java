package com.example.solva.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public final class LimitRequestDto implements Serializable {
    private final long accountNumber;
    private final long limitValue;
    private final String expenseCategory;
    private final String limitCurrency;
}
