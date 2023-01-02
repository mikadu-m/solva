package com.example.solva.service.impl;

import com.example.solva.dto.TransactionRequestDto;
import com.example.solva.service.ITransactionHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionHelper implements ITransactionHelper {

    private final TransactionService transactionService;

//    Parallel processing of different category transactions
    public void saveTransactionList(List<TransactionRequestDto> transactionRequestDtoList) {
        List<TransactionRequestDto> product_list = new ArrayList<>();
        List<TransactionRequestDto> service_list = new ArrayList<>();

        transactionRequestDtoList.forEach(t -> {
            if (Objects.equals(t.getExpenseCategory(), "product")) {
                product_list.add(t);
            } else {
                service_list.add(t);
            }
        });

        Runnable  myThread_1 = new MyRunnable(product_list, transactionService);
        Runnable  myThread_2 = new MyRunnable(service_list, transactionService);
        new Thread(myThread_1).start();
        new Thread(myThread_2).start();
    }

}
