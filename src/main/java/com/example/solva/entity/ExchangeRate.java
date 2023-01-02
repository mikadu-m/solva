package com.example.solva.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "exchange_rate_status")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "rate_value", precision = 6, scale = 2)
    private BigDecimal rateValue;

    @Column(name = "received_time")
    private Instant receivedTime;
}
