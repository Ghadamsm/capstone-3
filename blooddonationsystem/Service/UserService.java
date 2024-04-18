package com.example.blooddonationsystem.Service;

import com.example.blooddonationsystem.Api.ApiException;
import com.example.blooddonationsystem.Model.User;
import com.example.blooddonationsystem.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(Integer id, User updatedUser) {
        User user = userRepository.findUserById(id);

        if (user == null)
            throw new ApiException("User not found");

        user.setName(updatedUser.getName());
        user.setAge(updatedUser.getAge());
        user.setBloodType(updatedUser.getBloodType());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setEmail(updatedUser.getEmail());

        userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        User user = userRepository.findUserById(id);

        if (user == null)
            throw new ApiException("User not found");

        userRepository.delete(user);
    }




    //endpoint هياء

    public List<String> getBloodTypesUserCanDonate(String user_BloodType) {
        switch (user_BloodType) {
            case "A+":
                return List.of("A+", "AB+");
            case "A-":
                return List.of("A+", "A-", "AB+", "AB-");
            case "B+":
                return List.of("B+", "AB+");
            case "B-":
                return List.of("B+", "B-", "AB+", "AB-");
            case "AB+":
                return List.of("AB+");
            case "AB-":
                return List.of("AB+", "AB-");
            case "O+":
                return List.of("A+", "B+", "AB+", "O+");
            case "O-":
                return List.of("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
            default:
                return new ArrayList<>();
        }


    }
}
