package com.Vutata.ATM.Interface.service;

import com.Vutata.ATM.Interface.model.Transaction;

import com.Vutata.ATM.Interface.repo.TransactionRepo;
import com.Vutata.ATM.Interface.service.exceptions.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepo transactionRepo;

    public <S extends Transaction> S save(S entity) {
        S save = transactionRepo.save(entity);
        if (!transactionRepo.existsById(save.getId())) {throw new AccountNotFoundException("No existing transactions");}else {
            System.out.println("Transaction :"+save);

        }
        return save;
    }
    public Optional<Transaction> findById(Long id) {
        transactionRepo.findById(id).orElseThrow(()->new RuntimeException("No transactions exists on this account number"));

return transactionRepo.findById(id);
        }

    public List<Transaction> findAll() {
        List<Transaction> transactions = transactionRepo.findAll();

        if (transactions.isEmpty()) {
            throw new RuntimeException("No transactions found");
        }

        return transactions;
    }

}



