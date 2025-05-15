package com.example.demo.service.imp;

import com.example.demo.dto.UserDTO;
import com.example.demo.payload.request.signupRequest;

import java.util.List;

public interface LoginServiceimp {
    List<UserDTO> getAlluser();
    boolean emailExists (String email);
    String checkLogin(String email, String password);
    boolean login(String email, String password);
    boolean addUser(signupRequest signup);
    boolean changePassword(int id, String oldpassword, String newpassword);
}
