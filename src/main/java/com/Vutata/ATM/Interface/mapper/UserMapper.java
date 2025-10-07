package com.Vutata.ATM.Interface.mapper;

import com.Vutata.ATM.Interface.dto.UserDto;
import com.Vutata.ATM.Interface.model.User;

import java.util.stream.Collectors;

public class UserMapper {

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return new UserDto(
                user.getId(),
                user.getUserName(),
                user.getFailedLoginAttempts(),
                user.getLastLogin(),
                user.getRole(),
                user.getAccounts() != null
                        ? user.getAccounts().stream()
                        .map(account -> new BankAccountMapper().toDto(account))
                        .collect(Collectors.toList())
                        : null
        );
    }

    public User toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setId(dto.id());
        user.setUserName(dto.userName());
        user.setFailedLoginAttempts(dto.failedLoginAttempts());
        user.setLastLogin(dto.lastLogin());
        user.setRole(dto.role());

        if (dto.accounts() != null) {
            user.setAccounts(dto.accounts().stream()
                    .map(accountDto -> new BankAccountMapper().toEntity(accountDto))
                    .collect(Collectors.toList()));
        }

        return user;
    }
}
