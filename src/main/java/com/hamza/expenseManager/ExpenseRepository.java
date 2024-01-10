package com.hamza.expenseManager;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpenseRepository extends MongoRepository<expense,String> {

}
