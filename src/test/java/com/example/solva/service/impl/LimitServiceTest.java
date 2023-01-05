package com.example.solva.service.impl;

import com.example.solva.dto.TransactionRequestDto;
import com.example.solva.entity.Limit;
import com.example.solva.service.impl.LimitService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("classpath:/application-test.properties")
class LimitServiceTest {

    @Autowired
    private LimitService limitService;

    @Test
    void createLimit() {
        Limit limitActual = limitService.createLimit(1, 2000, "product", "USD");

        Limit limitExpected = new Limit();
        limitExpected.setAccountNumber(1);
        limitExpected.setLimitValue(2000);
        limitExpected.setExpenseCategory("product");
        limitExpected.setLimitCurrency("USD");
        limitExpected.setReceivedTime(Instant.now());
        assertThat(limitActual).usingRecursiveComparison()
                .ignoringFields("id","receivedTime")
                .isEqualTo(limitExpected);
    }

    @Test
    @Sql(value = {"/scripts/create-limit-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/scripts/create-limit-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getLimit(){
        List<Limit> limitListActual = limitService.getLimit(321);

        List<Limit> limitListExpected = new ArrayList<>(2);
        Limit limitSerExpected = new Limit();
        limitSerExpected.setAccountNumber(321);
        limitSerExpected.setLimitValue(2000);
        limitSerExpected.setExpenseCategory("product");
        limitSerExpected.setLimitCurrency("USD");
        limitSerExpected.setReceivedTime(null);
        limitListExpected.add(limitSerExpected);

        Limit limitProdExpected = new Limit();
        limitProdExpected.setAccountNumber(321);
        limitProdExpected.setLimitValue(2000);
        limitProdExpected.setExpenseCategory("service");
        limitProdExpected.setLimitCurrency("USD");
        limitProdExpected.setReceivedTime(null);
        limitProdExpected.setReceivedTime(null);
        limitListExpected.add(limitProdExpected);
        assertThat(limitListActual).isEqualTo(limitListExpected);
    }


    @Test
    void isLimitExceeded() {
        TransactionRequestDto transactionRequestDto = new TransactionRequestDto(322, 13131, "KZT",new BigDecimal(460) , "service");

        Limit limit = new Limit();
        boolean limitExceeded = limitService.isLimitExceeded(transactionRequestDto, limit);
        Assertions.assertTrue(limitExceeded);

        limit.setLimitValue(460);
        limitExceeded = limitService.isLimitExceeded(transactionRequestDto, limit);
        Assertions.assertFalse(limitExceeded);

        limit.setLimitValue(100);
        limitExceeded = limitService.isLimitExceeded(transactionRequestDto, limit);
        Assertions.assertTrue(limitExceeded);
    }
}