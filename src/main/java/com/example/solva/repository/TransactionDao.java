package com.example.solva.repository;

import com.example.solva.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.accountFrom = :accountNumber AND" +
            " t.expenseCategory = :expenseCategory AND " +
            "MONTH(t.receivedTime) = MONTH(current_date)")
    List<Transaction> findTransaction(long accountNumber, String expenseCategory);

    @Query(value = "SELECT t.*, l.limit_value " +
            "FROM transaction_status t " +
            "LEFT JOIN limit_status l ON t.limit_status = l.id " +
            "WHERE account_from = :accountNumber AND limit_exceeded = 'true'", nativeQuery = true)
    List<Transaction> findLimitExceededTransactions(@Param("accountNumber") long accountNumber);
}
