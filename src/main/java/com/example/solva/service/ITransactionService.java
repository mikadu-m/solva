package com.example.solva.service;

import com.example.solva.dto.TransactionRequestDto;

public interface ITransactionService {
    void saveTransaction(TransactionRequestDto transactionRequestDto);
}
