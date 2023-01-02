package com.example.solva.service.impl;

import com.example.solva.dto.TransactionItemDto;
import com.example.solva.dto.TransactionRequestDto;
import com.example.solva.entity.Limit;
import com.example.solva.entity.Transaction;
import com.example.solva.repository.LimitDao;
import com.example.solva.repository.TransactionDao;
import com.example.solva.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {
    private final ExchangeRateService exchangeRateService;
    private final TransactionDao transactionDao;
    private final LimitService limitService;
    private final LimitDao limitDao;

    // Save Transaction Entity in Database
    // Call isLimitExceeded method to define limit_exceeded flag
    // If no current limit is available, create new with '0' value and return limit_exceeded = true
    public void saveTransaction(final TransactionRequestDto transactionRequestDto) {

        boolean limit_status = true;
        Limit limit = limitDao.findMe(transactionRequestDto.getAccountFrom(),
                transactionRequestDto.getExpenseCategory());
        if (limit != null){
            limit_status = limitService.isLimitExceeded(transactionRequestDto, limit);
        } else {
            limit = limitService.createLimit(transactionRequestDto.getAccountFrom(),
                    0, transactionRequestDto.getExpenseCategory(),  "USD");
        }
        Transaction transaction = new Transaction();

        transaction.setAccountFrom(transactionRequestDto.getAccountFrom());
        transaction.setLimitId(limit);
        transaction.setAccountTo(transactionRequestDto.getAccountTo());
        transaction.setCurrencyShortname(transactionRequestDto.getCurrencyShortname());
        transaction.setSum(exchangeRateService.exchangeSum(transactionRequestDto.getSum()));
        transaction.setExpenseCategory(transactionRequestDto.getExpenseCategory());
        transaction.setReceivedTime(Instant.now());
        transaction.setLimitExceeded(limit_status);

        transactionDao.save(transaction);
    }

    // Get list of limit exceeded transactions with related limit details
    public List<TransactionItemDto> getTransactions(long accountNumber) {
        List<Transaction> transactionList = transactionDao.findLimitExceededTransactions(accountNumber);
        List<TransactionItemDto> transactionItems = new ArrayList<>();
        for (Transaction t: transactionList) {
            transactionItems.add(new TransactionItemDto(t));
        }
        return transactionItems;
    }

}
