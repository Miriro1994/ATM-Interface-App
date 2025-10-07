package com.Vutata.ATM.Interface.mapper;

import com.Vutata.ATM.Interface.dto.TransactionDto;
import com.Vutata.ATM.Interface.model.Transaction;

public class TransactionMapper {

    public TransactionDto toDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        return new TransactionDto(
                transaction.getId(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getTransactionDate(),
                transaction.getStatus(),
                transaction.getDescription(),
                transaction.getSourceAccount() != null ? transaction.getSourceAccount().getId() : 0,
                transaction.getTargetAccount() != null ? transaction.getTargetAccount().getId() : null
        );
    }

    public Transaction toEntity(TransactionDto dto) {
        if (dto == null) {
            return null;
        }

        Transaction transaction = new Transaction();
        transaction.setTransactionType(dto.transactionType());
        transaction.setAmount(dto.amount());
        transaction.setTransactionDate(dto.transactionDate());
        transaction.setStatus(dto.status());
        transaction.setDescription(dto.description());


        return transaction;
    }
}
