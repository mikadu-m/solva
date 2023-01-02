package com.example.solva.repository;

import com.example.solva.entity.ExchangeRate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ExchangeRateDao extends CrudRepository<ExchangeRate, UUID> {
    @Query(value = "SELECT * FROM exchange_rate_status where currency = :currency LIMIT 1", nativeQuery = true)
    ExchangeRate findTopByCurrency(String currency);
}
