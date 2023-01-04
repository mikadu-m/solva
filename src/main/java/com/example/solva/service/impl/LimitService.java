package com.example.solva.service.impl;

import com.example.solva.dto.LimitRequestDto;
import com.example.solva.dto.TransactionRequestDto;
import com.example.solva.entity.Limit;
import com.example.solva.entity.Transaction;
import com.example.solva.repository.LimitDao;
import com.example.solva.repository.TransactionDao;
import com.example.solva.service.ILimitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LimitService implements ILimitService {
    private final LimitDao limitDao;
    private final TransactionDao transactionDao;

    // Save Limit Entity in Database
    public Limit createLimit(long accountNumber, long limitValue, String expenseCategory, String limitCurrency) {
        Limit limit = new Limit();

        limit.setAccountNumber(accountNumber);
        limit.setLimitValue(limitValue);
        limit.setExpenseCategory(expenseCategory);
        limit.setLimitCurrency(limitCurrency);
        limit.setReceivedTime(Instant.now());

        limitDao.save(limit);

        return limit;
    }

    // Get limit details and save it as a new record in Database
    public void updateLimit(LimitRequestDto limitRequestDto) {
        long accountNumber = limitRequestDto.getAccountNumber();
        String expenseCategory = limitRequestDto.getExpenseCategory();
        long limitValue = limitRequestDto.getLimitValue();
        String limitCurrency = limitRequestDto.getLimitCurrency();

        createLimit(accountNumber, limitValue, expenseCategory, limitCurrency);
    }

    public List<Limit> getLimit(long accountNumber) {
        Limit limitProduct = limitDao.findMe(accountNumber, "product");
        Limit limitService = limitDao.findMe(accountNumber, "service");

        List<Limit> limitList= new ArrayList<>(2);
        limitList.add(limitProduct);
        limitList.add(limitService);
        return limitList;
    }

    // Check is current limit exceeded by transaction.
    public boolean isLimitExceeded(TransactionRequestDto transactionRequestDto, Limit limit) {
        long accountNumber = transactionRequestDto.getAccountFrom();
        String expenseCategory = transactionRequestDto.getExpenseCategory();
        BigDecimal new_sum = transactionRequestDto.getSum();

        List<Transaction> transactionList = transactionDao.findTransaction(accountNumber, expenseCategory);

        for (Transaction t: transactionList) {
            new_sum = new_sum.add(t.getSum());
        }
        return new_sum.compareTo(new BigDecimal(limit.getLimitValue())) == 1;

    }
}
