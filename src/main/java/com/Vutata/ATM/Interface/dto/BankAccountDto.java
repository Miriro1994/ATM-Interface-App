package com.Vutata.ATM.Interface.dto;

import com.Vutata.ATM.Interface.model.enums.AccountType;

import java.util.List;

public record BankAccountDto(
        long accountId,
        String accountNumber,
        double balance,
        AccountType accountType,
        long userId,
        List<TransactionDto> transactions,
        List<TransactionDto> incomingTransactions
) {}
