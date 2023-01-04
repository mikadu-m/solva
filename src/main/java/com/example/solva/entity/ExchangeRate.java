package com.example.solva.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Table("exchange_rate_status")
public class ExchangeRate {

    @Column("id")
    private UUID id;

    @Column("rate_value")
    private BigDecimal rateValue;

    @PrimaryKeyColumn(name = "currency", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String currency;

    @PrimaryKeyColumn(name = "received_time", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Instant receivedTime;
}
