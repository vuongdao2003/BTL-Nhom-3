package com.example.demo.repository;
import com.example.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    List<Users> findByEmailAndPassword(String email, String password);
    Users findByEmail(String email);

    boolean existsByEmailAndUserIDNot(String email, Integer userID);
    Users findByUserID(int userID);
}
