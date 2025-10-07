package com.Vutata.ATM.Interface.mapper;

import com.Vutata.ATM.Interface.dto.BankAccountDto;
import com.Vutata.ATM.Interface.model.BankAccount;

import java.util.stream.Collectors;

public class BankAccountMapper {

    public BankAccountDto toDto(BankAccount account) {
        if (account == null) {
            return null;
        }

        return new BankAccountDto(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getAccountType(),
                account.getUser() != null ? account.getUser().getId() : 0,
                account.getTransactions() != null
                        ? account.getTransactions().stream()
                        .map(tx -> new TransactionMapper().toDto(tx))
                        .collect(Collectors.toList())
                        : null,
                account.getIncomingTransactions() != null
                        ? account.getIncomingTransactions().stream()
                        .map(tx -> new TransactionMapper().toDto(tx))
                        .collect(Collectors.toList())
                        : null
        );
    }

    public BankAccount toEntity(BankAccountDto dto) {
        if (dto == null) {
            return null;
        }

        BankAccount account = new BankAccount();
        account.setId(dto.accountId());
        account.setAccountNumber(dto.accountNumber());
        account.setBalance(dto.balance());
        account.setAccountType(dto.accountType());


        if (dto.transactions() != null) {
            account.setTransactions(dto.transactions().stream()
                    .map(txDto -> new TransactionMapper().toEntity(txDto))
                    .collect(Collectors.toList()));
        }

        if (dto.incomingTransactions() != null) {
            account.setIncomingTransactions(dto.incomingTransactions().stream()
                    .map(txDto -> new TransactionMapper().toEntity(txDto))
                    .collect(Collectors.toList()));
        }

        return account;
    }
}
