package com.Vutata.ATM.Interface.service;


import com.Vutata.ATM.Interface.model.User;
import com.Vutata.ATM.Interface.repo.UserRepo;
import com.Vutata.ATM.Interface.service.exceptions.EmailExist;
import com.Vutata.ATM.Interface.service.exceptions.UserNotFound;
import com.Vutata.ATM.Interface.service.exceptions.WrongPin;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    @Autowired
    private final UserRepo userRepo;


    public <S extends User> S save(S entity) {

        if (entity == null) {throw new RuntimeException("User information can not be null");
        }
        if (entity.getUserName() == null || entity.getUserName().isBlank()) {throw new UserNotFound("User is not found");
        }
        if (entity.getUserPin()==null || entity.getUserPin().isBlank()) {throw new IllegalArgumentException("Pin can not be empty");
        }
        if (entity.getFailedLoginAttempts() == 3) { throw new IllegalStateException("Your account is blocked");
        }
        if (entity.getLastLogin() == null) { throw new IllegalStateException("Last login date cannot be null.");
        }
        if (entity.getRole() == null) {throw new IllegalArgumentException("User role cannot be null.");
        }
        if (!entity.getUserPin().matches("^*\\d{4}$")) {throw new WrongPin("Your pin should be four digits");
        }
        if (userRepo.findByEmail(entity.getEmail())) {throw new EmailExist("This emails is already used");

        }
        return userRepo.save(entity);
    }

    public Optional<User> findById(Long id) {
        userRepo.findById(id).orElseThrow(()->new UserNotFound("User not found"));
        return userRepo.findById(id);
    }

    public List<User> findAll() {
        List<User> all = userRepo.findAll();
        if (all.isEmpty()) throw new UserNotFound("Database has not been initialised");
        return all;
    }

    public void deleteById(Long id) {
        if (!userRepo.existsById(id)) {throw new UserNotFound("User doesn't exist");

        }
        userRepo.deleteById(id);
    }

}
