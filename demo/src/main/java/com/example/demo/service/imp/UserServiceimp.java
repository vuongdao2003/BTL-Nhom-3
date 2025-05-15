package com.example.demo.service.imp;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UseridDTO;

import java.util.List;

public interface UserServiceimp {
    List<UserDTO> getAlluser();
    boolean deleteuser(int id);
    String updateUser(UseridDTO user);
    List<UseridDTO> getuserbyid(int id);
}
