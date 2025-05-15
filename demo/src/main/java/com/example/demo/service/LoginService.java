package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Roles;
import com.example.demo.entity.Users;
import com.example.demo.payload.request.signupRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.imp.LoginServiceimp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoginService implements LoginServiceimp {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAlluser(){
        List<Users> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (Users user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getUserID());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
            userDTO.setFullname(user.getFullname());
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    @Override
    public boolean emailExists(String email) {
        Users user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false ;
    }

    @Override
    public String checkLogin(String email, String password){
    Users user = userRepository.findByEmail(email);
    if(user!=null&&passwordEncoder.matches(password,user.getPassword())){
        user.setLastTimeLogin(LocalDateTime.now());
        userRepository.save(user);
        return user.getRoles().getRoleName();
    }else {
        return null;
    }
    }


    @Override
    public boolean login(String email, String password) {
        List<Users> listUsers =userRepository.findByEmailAndPassword(email, password);
        if (listUsers.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean addUser(signupRequest signup) {
        Roles role = new Roles();
        role.setId(signup.getRoles());
        Users users = new Users();
        String password = passwordEncoder.encode(signup.getPassword());
        users.setEmail(signup.getEmail());
        users.setPassword(password);
        users.setFullname(signup.getFullName());
        users.setPhone(signup.getPhone());
        users.setRoles(role);
        users.setCreateDate(LocalDateTime.now());
        try {
            userRepository.save(users);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean changePassword(int id, String oldpassword, String newpassword) {
        Optional<?> optional = userRepository.findById(id);
        if(optional.isEmpty()){
            return false;
        }
        Users user = (Users) optional.get();
        if (!passwordEncoder.matches(oldpassword,user.getPassword())){
            return false;
        }
        String newpassword1 = passwordEncoder.encode(newpassword);
        user.setPassword(newpassword1);
        userRepository.save(user);
        return true;
    }

}
