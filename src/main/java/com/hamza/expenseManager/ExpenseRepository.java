package com.hamza.expenseManager;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<expense,Long> {

}
