package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UseridDTO;
import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.imp.UserServiceimp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class UserService implements UserServiceimp {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public List<UserDTO> getAlluser(){
        List<Users> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (Users user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setRole(user.getRoles().getRoleName());
            userDTO.setId(user.getUserID());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
            userDTO.setFullname(user.getFullname());
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    @Override
    public boolean deleteuser(int id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public String updateUser(UseridDTO useridDTO) {
        Optional<Users> userOptional = userRepository.findById(useridDTO.getId());
        if (userOptional.isEmpty()) {
            return "Không tìm thấy user cần cập nhật";
        }

        Users user = userOptional.get();

        // Cập nhật fullname nếu có giá trị
        if (useridDTO.getFullname() != null && !useridDTO.getFullname().isBlank()) {
            user.setFullname(useridDTO.getFullname());
        }

        // Cập nhật email nếu có giá trị
        if (useridDTO.getEmail() != null && !useridDTO.getEmail().isBlank()) {
            // Kiểm tra định dạng email
            if (!isValidEmail(useridDTO.getEmail())) {
                return "Email không đúng định dạng";
            }

            // Kiểm tra trùng email
            boolean emailExists = userRepository.existsByEmailAndUserIDNot(useridDTO.getEmail(), useridDTO.getId());
            if (emailExists) {
                return "Email đã được sử dụng";
            }

            user.setEmail(useridDTO.getEmail());
        } else if (useridDTO.getEmail() != null) {
            return "Email không được để trống";
        }

        userRepository.save(user);
        return "Cập nhật thành công";
    }

    // Hàm kiểm tra định dạng email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }



    @Override
    public List<UseridDTO> getuserbyid(int id) {
        Optional<Users> users = userRepository.findById(id);
        List<UseridDTO> userDTOS = new ArrayList<>();
        if(users.isPresent()){
            UseridDTO useridDTO = new UseridDTO();
            useridDTO.setId(users.get().getUserID());
            useridDTO.setEmail(users.get().getEmail());
            useridDTO.setFullname(users.get().getFullname());
            useridDTO.setPhone(users.get().getPhone());
            userDTOS.add(useridDTO);
        }
        return userDTOS;
    }


}
