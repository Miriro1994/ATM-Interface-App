package com.Vutata.ATM.Interface.mapper;

import com.Vutata.ATM.Interface.dto.UserDto;
import com.Vutata.ATM.Interface.dto.BankAccountDto;
import com.Vutata.ATM.Interface.dto.TransactionDto;
import com.Vutata.ATM.Interface.model.User;
import com.Vutata.ATM.Interface.model.BankAccount;
import com.Vutata.ATM.Interface.model.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class MapperConfig {

    private static final UserMapper userMapper = new UserMapper();
    private static final BankAccountMapper bankAccountMapper = new BankAccountMapper();
    private static final TransactionMapper transactionMapper = new TransactionMapper();

    // ------------------ User ------------------
    public static UserDto toDto(User user) {
        return userMapper.toDto(user);
    }

    public static User toEntity(UserDto dto) {
        return userMapper.toEntity(dto);
    }

    public static List<UserDto> toUserDtoList(List<User> users) {
        return users == null ? null : users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<User> toUserEntityList(List<UserDto> dtos) {
        return dtos == null ? null : dtos.stream()
                .map(userMapper::toEntity)
                .collect(Collectors.toList());
    }

    // ------------------ BankAccount ------------------
    public static BankAccountDto toDto(BankAccount account) {
        return bankAccountMapper.toDto(account);
    }

    public static BankAccount toEntity(BankAccountDto dto) {
        return bankAccountMapper.toEntity(dto);
    }

    public static List<BankAccountDto> toBankAccountDtoList(List<BankAccount> accounts) {
        return accounts == null ? null : accounts.stream()
                .map(bankAccountMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<BankAccount> toBankAccountEntityList(List<BankAccountDto> dtos) {
        return dtos == null ? null : dtos.stream()
                .map(bankAccountMapper::toEntity)
                .collect(Collectors.toList());
    }

    // ------------------ Transaction ------------------
    public static TransactionDto toDto(Transaction transaction) {
        return transactionMapper.toDto(transaction);
    }

    public static Transaction toEntity(TransactionDto dto) {
        return transactionMapper.toEntity(dto);
    }

    public static List<TransactionDto> toTransactionDtoList(List<Transaction> transactions) {
        return transactions == null ? null : transactions.stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<Transaction> toTransactionEntityList(List<TransactionDto> dtos) {
        return dtos == null ? null : dtos.stream()
                .map(transactionMapper::toEntity)
                .collect(Collectors.toList());
    }
}
