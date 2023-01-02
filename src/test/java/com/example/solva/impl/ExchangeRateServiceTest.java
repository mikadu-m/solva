package com.example.solva.impl;

import com.example.solva.service.impl.ExchangeRateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SpringBootTest
@TestPropertySource("classpath:/application-test.properties")
class ExchangeRateServiceTest {
    @Autowired
    private ExchangeRateService exchangeRateService;

    @Sql(value = {"/scripts/create-exchangeRate-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/scripts/create-exchangeRate-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void exchangeSum() {
        BigDecimal bigDecActual = exchangeRateService.exchangeSum(new BigDecimal(460));
        BigDecimal bigDecExpected = new BigDecimal(460);
        bigDecExpected = bigDecExpected.divide(new BigDecimal(460),2, RoundingMode.HALF_UP);
        Assertions.assertEquals(bigDecActual,bigDecExpected);
    }
}