package com.example.solva;

import com.example.solva.dto.LimitRequestDto;
import com.example.solva.dto.TransactionRequestDto;
import com.example.solva.service.impl.ExchangeRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:/application-test.properties")
public class RestControllerTest {
    @Autowired
    private ExchangeRateService exchangeRateService;
    @Autowired
    private MockMvc mockMvc;
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Test
    public void saveTransaction() throws Exception {
        TransactionRequestDto anObject = new TransactionRequestDto(322, 13131, "KZT",new BigDecimal(46000) , "service");
        //https://stackoverflow.com/questions/20504399/testing-springs-requestbody-using-spring-mockmvc

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(anObject );

        mockMvc.perform(post("http://localhost:8080/api/v1/solva/save").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void updateLimit() throws Exception {
        LimitRequestDto anObject = new LimitRequestDto(1, 2000, "product", "USD");
        //https://stackoverflow.com/questions/20504399/testing-springs-requestbody-using-spring-mockmvc

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(anObject );

        mockMvc.perform(post("http://localhost:8080/api/v1/solva/save").contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(value = {"/scripts/create-transaction-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/scripts/create-transaction-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getTransaction() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/solva/transactions/322").contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].limitValue", is(2000)))
                .andExpect(jsonPath("$[0].limitDatetime", is("2022-12-29T06:55:01.933580Z")))
                .andExpect(jsonPath("$[0].limitCurrency", is("USD")))
                .andExpect(jsonPath("$[0].accountFrom", is(322)))
                .andExpect(jsonPath("$[0].accountTo", is(13131)))
                .andExpect(jsonPath("$[0].currencyShortname", is("KZT")))
                .andExpect(jsonPath("$[0].sum", is(46000.0)))
                .andExpect(jsonPath("$[0].expenseCategory", is("service")))
                .andExpect(jsonPath("$[0].limitExceeded", is(true)));

//                ;"<{id=1, limitValue=2000, limitDatetime=2022-12-29T06:55:01.933580 limitExceeded=true}>")));
//                .andExpect(content().json("{\"transactionList\":[{\"id\":1,\"limitValue\":2000,\"limitDatetime\":\"2022-12-29T06:55:01.933580Z\",\"limitCurrency\":\"USD\",\"accountFrom\":322,\"accountTo\":13131,\"currencyShortname\":\"KZT\",\"sum\":46000.00,\"expenseCategory\":\"service\",\"limitExceeded\":true}]}"));
    }
}
