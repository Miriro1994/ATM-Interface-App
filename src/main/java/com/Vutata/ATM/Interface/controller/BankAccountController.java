package com.Vutata.ATM.Interface.controller;

import com.Vutata.ATM.Interface.model.BankAccount;
import com.Vutata.ATM.Interface.model.Transaction;
import com.Vutata.ATM.Interface.service.BankAccountService;
import com.Vutata.ATM.Interface.service.exceptions.InvalidOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @Autowired
    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    // CREATE ACCOUNT
    @PostMapping("/create")
    public ResponseEntity<BankAccount> createAccount(@RequestBody BankAccount account) {
        BankAccount createdAccount = bankAccountService.createAccount(account);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    // FIND ACCOUNT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> getAccountById(@PathVariable("id") Long id) {
        BankAccount account = bankAccountService.findAccountById(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    // DEPOSIT
    @PostMapping("/{id}/deposit")
    public ResponseEntity<BankAccount> deposit(@PathVariable("id") Long accountId,
                                               @RequestParam double amount) {
        BankAccount updatedAccount = bankAccountService.deposit(accountId, amount);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    // WITHDRAW
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<BankAccount> withdraw(@PathVariable("id") Long accountId,
                                                @RequestParam double amount) {
        BankAccount updatedAccount = bankAccountService.withdraw(accountId, amount);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    // TRANSFER
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam Long fromAccountId,
                                           @RequestParam Long toAccountId,
                                           @RequestParam double amount) {
        bankAccountService.transfer(fromAccountId, toAccountId, amount);
        return new ResponseEntity<>("Transfer successful", HttpStatus.OK);
    }

    // CHECK BALANCE
    @GetMapping("/{id}/balance")
    public ResponseEntity<Double> checkBalance(@PathVariable("id") Long accountId) {
        double balance = bankAccountService.checkBalance(accountId);
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }

    // VIEW ALL ACCOUNTS
    @GetMapping
    public ResponseEntity<List<BankAccount>> getAllAccounts() {
        List<BankAccount> accounts = bankAccountService.findAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    // VIEW TRANSACTIONS FOR AN ACCOUNT
    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<Transaction>> getAccountTransactions(@PathVariable("id") Long accountId) {
        List<Transaction> transactions = bankAccountService.getAccountTransactions(accountId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    // Handle general invalid operations
    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<String> handleInvalidOperation(InvalidOperationException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
