package com.example.solva.impl;

import com.example.solva.dto.TransactionRequestDto;
import com.example.solva.entity.Limit;
import com.example.solva.service.impl.LimitService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("classpath:/application-test.properties")
class LimitServiceTest {

    @Autowired
    private LimitService limitService;

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
}