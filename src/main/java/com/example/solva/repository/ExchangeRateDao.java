package com.example.solva.repository;

import com.example.solva.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateDao extends JpaRepository<ExchangeRate, Long> {
    ExchangeRate findFirstByOrderByReceivedTime();
}
