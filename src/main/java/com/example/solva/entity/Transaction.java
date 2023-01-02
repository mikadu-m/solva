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
@Table(name = "transaction_status")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "limit_status")
    private Limit limitId;

    @Column(name = "account_from")
    private long accountFrom;

    @Column(name = "account_to")
    private long accountTo;

    private BigDecimal sum;

    @Column(name = "limit_exceeded")
    private boolean limitExceeded;

    @Column(name = "expense_category")
    private String expenseCategory;

    @Column(name = "currency_shortname")
    private String currencyShortname;

    @Column(name = "received_time")
    private Instant receivedTime;
}
