package com.example.blooddonationsystem.Repository;


import com.example.blooddonationsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserById(int id);
}
