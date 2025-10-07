package com.Vutata.ATM.Interface.repo;

import com.Vutata.ATM.Interface.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BankAccountRepo extends JpaRepository<BankAccount,Long> {


}
