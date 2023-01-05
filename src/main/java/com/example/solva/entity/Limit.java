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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Limit limit = (Limit) o;

        if (accountNumber != limit.accountNumber) return false;
        if (limitValue != limit.limitValue) return false;
        if (expenseCategory != null ? !expenseCategory.equals(limit.expenseCategory) : limit.expenseCategory != null)
            return false;
        return limitCurrency != null ? limitCurrency.equals(limit.limitCurrency) : limit.limitCurrency == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (accountNumber ^ (accountNumber >>> 32));
        result = 31 * result + (int) (limitValue ^ (limitValue >>> 32));
        result = 31 * result + (expenseCategory != null ? expenseCategory.hashCode() : 0);
        result = 31 * result + (limitCurrency != null ? limitCurrency.hashCode() : 0);
        return result;
    }
}
