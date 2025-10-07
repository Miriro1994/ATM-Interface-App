package com.Vutata.ATM.Interface.dto;

import com.Vutata.ATM.Interface.model.enums.Description;
import com.Vutata.ATM.Interface.model.enums.Status;
import com.Vutata.ATM.Interface.model.enums.TransactionType;

import java.time.LocalDateTime;

public record TransactionDto(
        long Id,
        TransactionType transactionType,
        double amount,
        LocalDateTime transactionDate,
        Status status,
        Description description,
        long sourceAccountId,
        Long targetAccountId
) {}
