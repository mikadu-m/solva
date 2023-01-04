package com.example.solva.service.impl;

import com.example.solva.dto.TransactionRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyRunnable implements Runnable  {
    private final TransactionService transactionService;
    private final List<TransactionRequestDto> transactionRequestDtoList;
    public MyRunnable(List<TransactionRequestDto> transactionRequestDtoList, TransactionService transactionService) {
        this.transactionRequestDtoList = transactionRequestDtoList;
        this.transactionService = transactionService;
    }

//    Processing of each transaction
    public void run() {
        transactionRequestDtoList.forEach(transactionService::saveTransaction);
    }
}
