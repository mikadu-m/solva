package com.example.solva.service.impl;

import com.example.solva.entity.ExchangeRate;
import com.example.solva.repository.ExchangeRateDao;
import com.example.solva.service.IExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.UUID;

@EnableScheduling
@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateService implements IExchangeRateService {
    @Value("${constant.twelvedata.url}")
    private String url;

    @Value("${constant.twelvedata.api-key}")
    private String key;
    private static final String CURRENCY = "USD/";
    private final ExchangeRateDao exchangeRateDao;
    private final RestTemplate restTemplate;

    // Exchange sum from KZT to USD currency
    public BigDecimal exchangeSum(BigDecimal sum, String currency) {
        ExchangeRate currentRate = exchangeRateDao.findTopByCurrency(CURRENCY+currency);

        if (currentRate != null){
            return sum.divide(currentRate.getRateValue(),2, RoundingMode.HALF_UP);
        } else {
            log.info("No currentRate is available");
            return sum;
        }
    }

    // Save ExchangeRate in Cassandra Database every 24 hours at 11:00 Washington.
    // For testing - every 30 sec - @Scheduled(fixedRate = 10000)
     @Scheduled(cron = "0 0 23 * * MON-FRI", zone = "GMT-5:00")
    public void createExchangeRate() {

        ExchangeRate exchangeRate = new ExchangeRate();

        exchangeRate.setCurrency("USD/RUB");
        exchangeRate.setId(UUID.randomUUID());
        exchangeRate.setRateValue(requestExchangeRate("USD/RUB"));
        exchangeRate.setReceivedTime(Instant.now());
        exchangeRateDao.save(exchangeRate);

        exchangeRate.setCurrency("USD/KZT");
        exchangeRate.setId(UUID.randomUUID());
        exchangeRate.setRateValue(requestExchangeRate("USD/KZT"));
        exchangeRate.setReceivedTime(Instant.now());
        exchangeRateDao.save(exchangeRate);
    }

    // HTTP GET request for ExchangeRate from Twelvedata
    public BigDecimal requestExchangeRate(String currency) {
        String full_url = String.format("%s?symbol=%s&interval=1day&apikey=%s", url, currency, key);
        String response =  restTemplate.getForObject(full_url, String.class);
        try {
            JSONObject obj = new JSONObject(response);
            return BigDecimal.valueOf(
                    obj.getJSONArray("values")
                            .getJSONObject(0)
                            .getDouble("close"))
                    .setScale(2, RoundingMode.HALF_UP);
        } catch (JSONException e) {
            log.error("JSONException - { }", e);
        }
        return new BigDecimal(0);
    }
}
