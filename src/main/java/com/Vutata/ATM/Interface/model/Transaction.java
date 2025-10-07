package com.Vutata.ATM.Interface.model;

import com.Vutata.ATM.Interface.model.enums.Description;
import com.Vutata.ATM.Interface.model.enums.Status;
import com.Vutata.ATM.Interface.model.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @NotNull(message = "Transaction type cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "TransactionType", nullable = false)
    TransactionType transactionType;

    @NotNull(message = "Amount cannot be null")
    @Column(name = "Amount", nullable = false)
    private double amount;

    @Column(name = "Transaction_date", nullable = false)
    private LocalDateTime transactionDate = LocalDateTime.now();

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    Status status;

    @Column(name = "description")
    @Enumerated(EnumType.STRING)
    Description description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id", nullable = false)
    private BankAccount sourceAccount; // Account performing the transaction

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_account_id")
    private BankAccount targetAccount; // For TRANSFER, can be null for DEPOSIT/WITHDRAW
}
