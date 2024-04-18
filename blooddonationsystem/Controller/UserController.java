package com.example.blooddonationsystem.Controller;

import com.example.blooddonationsystem.Model.User;
import com.example.blooddonationsystem.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get-all")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user) {
        userService.addUser(user);
        return ResponseEntity.status(200).body("User added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id, @RequestBody @Valid User user) {
        userService.updateUser(id, user);
        return ResponseEntity.status(200).body("User updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.status(200).body("User deleted successfully");
    }

    // endpoint
    @GetMapping("/bloodtypes/{user_BloodType}")
    public List<String> getBloodTypesUserCanDonate(@PathVariable String user_BloodType) {
        return userService.getBloodTypesUserCanDonate(user_BloodType);
    }

}
