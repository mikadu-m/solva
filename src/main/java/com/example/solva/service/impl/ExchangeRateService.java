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

@EnableScheduling
@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateService implements IExchangeRateService {
    @Value("${constant.twelvedata.url}")
    private String url;

    @Value("${constant.twelvedata.api-key}")
    private String key;
    private static final String currency = "USD/KZT";
    private final ExchangeRateDao exchangeRateDao;
    private final RestTemplate restTemplate;

    // Exchange sum from KZT to USD currency
    public BigDecimal exchangeSum(BigDecimal sum) {
        ExchangeRate currentRate = exchangeRateDao.findFirstByOrderByReceivedTime();

        if (currentRate != null){
            return sum.divide(currentRate.getRateValue(),2, RoundingMode.HALF_UP);
        } else {
            log.info("No currentRate is available");
            return sum;
        }
    }

    // Save ExchangeRate Entity in Database every 24 hours at 11:00 Washington.
    // For testing - every 30 sec - @Scheduled(fixedRate = 30000)
    @Scheduled(cron = "0 0 23 * * MON-FRI", zone = "GMT-5:00")
    public void createExchangeRate() {
        Instant time = Instant.now();

        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setRateValue(requestExchangeRate(currency));
        exchangeRate.setReceivedTime(time);

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
