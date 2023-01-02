package com.example.solva.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "limit_status")
public class Limit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "account_number")
    private long accountNumber;

    @Column(name = "limit_value")
    private long limitValue;

    @Column(name = "expense_category")
    private String expenseCategory;

    @Column(name = "limit_currency")
    private String limitCurrency;

    @Column(name = "received_time")
    private Instant receivedTime;
}
