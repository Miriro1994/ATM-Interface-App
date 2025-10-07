package com.Vutata.ATM.Interface.dto;

import com.Vutata.ATM.Interface.model.enums.Role;

import java.time.LocalDateTime;
import java.util.List;

public record UserDto(
        long id,
        String userName,
        int failedLoginAttempts,
        LocalDateTime lastLogin,
        Role role,
        List<BankAccountDto> accounts
) {}
