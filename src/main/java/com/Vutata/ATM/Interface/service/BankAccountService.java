package com.Vutata.ATM.Interface.service;

import com.Vutata.ATM.Interface.model.BankAccount;

import com.Vutata.ATM.Interface.model.Transaction;
import com.Vutata.ATM.Interface.model.enums.Status;
import com.Vutata.ATM.Interface.model.enums.TransactionType;
import com.Vutata.ATM.Interface.repo.BankAccountRepo;
import com.Vutata.ATM.Interface.repo.TransactionRepo;
import com.Vutata.ATM.Interface.service.exceptions.AccountNotFoundException;
import com.Vutata.ATM.Interface.service.exceptions.InsufficientFundsException;
import com.Vutata.ATM.Interface.service.exceptions.InvalidOperationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BankAccountService {

 private  final BankAccountRepo bankAccountRepo;
 private  final TransactionRepo transactionRepo;
    //CREATE ACCOUNT
    public BankAccount createAccount(BankAccount account) {
        if (account == null) {
            throw new InvalidOperationException("Account data cannot be null");
        }

        account.setBalance(0.0);
        account.setCreatedAt(LocalDateTime.now());

        return bankAccountRepo.save(account);
    }

    //FIND ACCOUNT
    public BankAccount findAccountById(Long id) {
        Optional<BankAccount> optionalAccount = bankAccountRepo.findById(id);
        if (!optionalAccount.isPresent()) {
            throw new AccountNotFoundException("No account found with ID: " + id);
        }
        return optionalAccount.get();
    }

    //DEPOSIT
    @Transactional
    public BankAccount deposit(Long accountId, double amount) {
        if (amount <= 0) {
            throw new InvalidOperationException("Deposit amount must be greater than zero");
        }

        BankAccount account = findAccountById(accountId);

        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);

        bankAccountRepo.save(account);
        recordTransaction(account, amount, TransactionType.DEPOSIT);

        return account;
    }

    //WITHDRAW
    @Transactional
    public BankAccount withdraw(Long accountId, double amount) {
        if (amount <= 0) {
            throw new InvalidOperationException("Withdrawal amount must be greater than zero");
        }

        BankAccount account = findAccountById(accountId);

        if (account.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal");
        }

        double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);

        bankAccountRepo.save(account);
        recordTransaction(account, amount, TransactionType.WITHDRAW);

        return account;
    }

    //TRANSFER
    @Transactional
    public void transfer(Long fromAccountId, Long toAccountId, double amount) {
        if (fromAccountId == null || toAccountId == null) {
            throw new InvalidOperationException("Account IDs cannot be null");
        }

        if (fromAccountId.equals(toAccountId)) {
            throw new InvalidOperationException("Cannot transfer to the same account");
        }

        if (amount <= 0) {
            throw new InvalidOperationException("Transfer amount must be greater than zero");
        }

        BankAccount sender = findAccountById(fromAccountId);
        BankAccount receiver = findAccountById(toAccountId);

        if (sender.getBalance() < amount) {
            throw new InsufficientFundsException("Sender has insufficient funds");
        }

        // Perform transfer
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        bankAccountRepo.save(sender);
        bankAccountRepo.save(receiver);

        // Record both transactions
        recordTransaction(sender, amount, TransactionType.TRANSFER_OUT);
        recordTransaction(receiver, amount, TransactionType.TRANSFER_IN);
    }

    //CHECK BALANCE
    public double checkBalance(Long accountId) {
        BankAccount account = findAccountById(accountId);
        return account.getBalance();
    }

    //VIEW ALL ACCOUNTS
    public List<BankAccount> findAllAccounts() {
        List<BankAccount> accounts = bankAccountRepo.findAll();
        if (accounts.isEmpty()) {
            throw new InvalidOperationException("No bank accounts found in the system");
        }
        return accounts;
    }

    //VIEW TRANSACTIONS
    public List<Transaction> getAccountTransactions(Long accountId) {
        BankAccount account = findAccountById(accountId);
        List<Transaction> transactions = transactionRepo.findBySourceAccount(account);
        if (transactions.isEmpty()) {
            throw new InvalidOperationException("No transactions found for this account");
        }
        return transactions;
    }

    //RECORD TRANSACTION
    private void recordTransaction(BankAccount account, double amount,
                                   TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(type);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus(Status.SUCCESSFUL);
        transaction.setSourceAccount(account);
        transactionRepo.save(transaction);
    }


}
