package com.example.solva.service.impl;

import com.datastax.oss.driver.api.core.CqlSession;
import com.example.solva.service.impl.ExchangeRateService;
import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
@TestPropertySource("classpath:/application-test.properties")
class ExchangeRateServiceTest {
//    @Autowired
//    private ExchangeRateService exchangeRateService;

//    @Before
//    public void setUp() throws Exception {
////        EmbeddedCassandraServerHelper.startEmbeddedCassandra();
////        CqlSession session = EmbeddedCassandraServerHelper.getSession();
//        CqlSession session = CqlSession.builder()
//                .withLocalDatacenter("datacenter1")
//                .withKeyspace("spring_cassandra_test")
//                .build();
//        new CQLDataLoader(session).load(new ClassPathCQLDataSet("/scripts/create-exchangeRate-before.cql"));
//    }

//    @Sql(value = {"/scripts/create-exchangeRate-before.cql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(value = {"/scripts/create-exchangeRate-after.cql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void exchangeSum() {
//        BigDecimal bigDecActual = exchangeRateService.exchangeSum(new BigDecimal(460), "KZT");
//        BigDecimal bigDecExpected = new BigDecimal(460);
//        bigDecExpected = bigDecExpected.divide(new BigDecimal(460),2, RoundingMode.HALF_UP);
//        Assertions.assertEquals(bigDecActual,bigDecExpected);
    }
//    @After
//    public void tearDown() throws Exception {
//        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
//    }
}