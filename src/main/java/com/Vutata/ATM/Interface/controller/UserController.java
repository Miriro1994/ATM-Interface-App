package com.Vutata.ATM.Interface.controller;

import com.Vutata.ATM.Interface.model.User;
import com.Vutata.ATM.Interface.service.UserService;
import com.Vutata.ATM.Interface.service.exceptions.UserNotFound;
import com.Vutata.ATM.Interface.service.exceptions.EmailExist;
import com.Vutata.ATM.Interface.service.exceptions.WrongPin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // CREATE or UPDATE user
    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try {
            User savedUser = userService.save(user);
            return ResponseEntity.ok(savedUser);
        } catch (EmailExist e) {
            return ResponseEntity.badRequest().body("Email already exists: " + e.getMessage());
        } catch (WrongPin e) {
            return ResponseEntity.badRequest().body("Invalid PIN: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error saving user: " + e.getMessage());
        }
    }

    // GET user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.findById(id).get());
        } catch (UserNotFound e) {
            return ResponseEntity.status(404).body("User not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error fetching user: " + e.getMessage());
        }
    }

    // GET all users
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.findAll();
            return ResponseEntity.ok(users);
        } catch (UserNotFound e) {
            return ResponseEntity.status(404).body("No users found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error retrieving users: " + e.getMessage());
        }
    }

    // DELETE user by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.ok("User deleted successfully.");
        } catch (UserNotFound e) {
            return ResponseEntity.status(404).body("User not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting user: " + e.getMessage());
        }
    }
}
