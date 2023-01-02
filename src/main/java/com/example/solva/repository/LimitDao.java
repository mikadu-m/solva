package com.example.solva.repository;

import com.example.solva.entity.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LimitDao extends JpaRepository<Limit, Long> {
    @Query(value = "SELECT l.* FROM limit_status l " +
            "WHERE account_number = :accountNumber AND expense_category = :expenseCategory " +
            "ORDER BY received_time DESC LIMIT 1", nativeQuery = true)
    Limit findMe(@Param("accountNumber") long accountNumber,
                           @Param("expenseCategory")  String expenseCategory);
}
