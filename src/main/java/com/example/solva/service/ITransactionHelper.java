package com.example.solva.service;

import com.example.solva.dto.TransactionRequestDto;

import java.util.List;

public interface ITransactionHelper {
    void saveTransactionList(List<TransactionRequestDto> transactionRequestDtoList);
}
