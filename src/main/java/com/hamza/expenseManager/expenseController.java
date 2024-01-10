package com.hamza.expenseManager;

import java.util.List;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expenses")
public class expenseController {
    private final ExpenseRepository expenseRepository;

    @Autowired
    public expenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllExpenses() {
        try {
            List<expense> expenses = expenseRepository.findAll();
            return ResponseEntity.ok(new ApiResponse(true, "Expenses retrieved successfully", expenses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error retrieving expenses", null));
        }
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        try {
            return ResponseEntity.ok("Hello retrieved successfully");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving Hello");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getExpenseById(@PathVariable String id) {
        try {
            expense currrentExpense = expenseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));

            return ResponseEntity.ok(new ApiResponse(true, "Todo retrieved successfully", currrentExpense));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Error retrieving todo with id: " + id, null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createExpense(@RequestBody expense expense) {
        try {
            expense createdExpense = expenseRepository.save(expense);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Expense created successfully", createdExpense));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error creating todo", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateExpense(@PathVariable String id, @RequestBody expense expenseDetails) {
        try {
            expense expense = expenseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
            double amount = 500;
            expense.setTitle(expenseDetails.getTitle());
            expense.setAmount(expenseDetails.getAmount());

            expense updatedExpense = expenseRepository.save(expense);
            return ResponseEntity.ok(new ApiResponse(true, "Expense updated successfully", updatedExpense));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Error updating expense with id: " + id, null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteExpense(@PathVariable String id) {
        try {
            expense expense = expenseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

            expenseRepository.delete(expense);
            return ResponseEntity.ok(new ApiResponse(true, "Todo deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "Error deleting todo with id: " + id, null));
        }
    }

}
