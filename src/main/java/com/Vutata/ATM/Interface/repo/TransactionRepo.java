package com.Vutata.ATM.Interface.repo;

import com.Vutata.ATM.Interface.model.BankAccount;
import com.Vutata.ATM.Interface.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {


    List<Transaction> findBySourceAccount(BankAccount account);

}

