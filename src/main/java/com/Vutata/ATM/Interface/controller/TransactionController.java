package com.Vutata.ATM.Interface.controller;

import com.Vutata.ATM.Interface.model.Transaction;
import com.Vutata.ATM.Interface.service.TransactionService;
import com.Vutata.ATM.Interface.service.exceptions.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Save a transaction
    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
        try {
            Transaction savedTransaction = transactionService.save(transaction);
            return ResponseEntity.ok(savedTransaction);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while saving the transaction.");
        }
    }

    // Find transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long id) {
        try {
            Optional<Transaction> transaction = transactionService.findById(id);
            if (transaction.isPresent()) {
                return ResponseEntity.ok(transaction.get());
            } else {
                return ResponseEntity.status(404).body("Transaction not found with ID: " + id);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred.");
        }
    }

    // Get all transactions
    @GetMapping
    public ResponseEntity<?> getAllTransactions() {
        try {
            return ResponseEntity.ok(transactionService.findAll());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Unable to fetch transactions.");
        }
    }
}
