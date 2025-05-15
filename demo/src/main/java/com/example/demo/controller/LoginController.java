package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Users;
import com.example.demo.payload.ResponData;
import com.example.demo.payload.request.ChangePasswordRequest;
import com.example.demo.payload.request.signupRequest;
import com.example.demo.service.imp.LoginServiceimp;
import com.example.demo.untils.JwtUntilHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/login")

public class LoginController {
    @Autowired
    LoginServiceimp LoginServiceimp;
    @Autowired
    JwtUntilHelper jwtUntilHelper;
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String email, @RequestParam String password) {
        ResponData responseData = new ResponData();

        // 1. Kiểm tra các trường không được bỏ trống
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            responseData.setSuccess(false);
            responseData.setMessage("Email và Password không được bỏ trống");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

        // 2. Kiểm tra thông tin đăng nhập
        String checkLogin = LoginServiceimp.checkLogin(email, password);
        System.out.println(checkLogin);
        if (checkLogin != null) {
            String token = jwtUntilHelper.generateToken(email,checkLogin);
            responseData.setSuccess(true);
            responseData.setData(token);
            responseData.setMessage("Đăng nhập thành công");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setSuccess(false);
            responseData.setMessage("Email và Password không hợp lệ");
            return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody signupRequest signupRequest) {
        ResponData ResponData = new ResponData();
        if(!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            ResponData.setSuccess(false);
            ResponData.setMessage("Sai mật khẩu");
            return new ResponseEntity<>(ResponData, HttpStatus.BAD_REQUEST);
        }

        if (signupRequest.getFullName() == null || signupRequest.getFullName().isEmpty() ||
                signupRequest.getPhone() == null || signupRequest.getPhone().isEmpty() ||
                signupRequest.getEmail() == null || signupRequest.getEmail().isEmpty() ||
                signupRequest.getPassword() == null || signupRequest.getPassword().isEmpty() ||
                signupRequest.getConfirmPassword() == null || signupRequest.getConfirmPassword().isEmpty()) {

            ResponData.setSuccess(false);
            ResponData.setMessage("Các trường không được bỏ trống");
            return new ResponseEntity<>(ResponData, HttpStatus.BAD_REQUEST);
        }
        if(LoginServiceimp.emailExists(signupRequest.getEmail())) {
            ResponData.setSuccess(false);
            ResponData.setMessage("Email đã tồn tại");
            return new ResponseEntity<>(ResponData, HttpStatus.CONFLICT);
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!signupRequest.getEmail().matches(emailRegex)) {
            ResponData.setSuccess(false);
            ResponData.setMessage("Email không đúng định dạng");
            return new ResponseEntity<>(ResponData, HttpStatus.BAD_REQUEST);
        }
        boolean creareUser = LoginServiceimp.addUser(signupRequest);
        if(creareUser) {
            ResponData.setSuccess(true);
            ResponData.setMessage("Tạo tài khoản thành công");
            return new ResponseEntity<>(ResponData, HttpStatus.CREATED);
        }else{
            ResponData.setSuccess(false);
            ResponData.setMessage("Tạo tài khoản thất bại");
            return new ResponseEntity<>(ResponData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/home")
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> userDTOS = LoginServiceimp.getAlluser();
        return new ResponseEntity<>( userDTOS, HttpStatus.OK);
    }


    @PostMapping("/{id}/ChangePassword")
    public ResponseEntity<?> changePassword(@PathVariable int id, @RequestBody ChangePasswordRequest changePasswordRequest) {
        ResponData ResponData = new ResponData();

        if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            ResponData.setSuccess(Boolean.FALSE);
            ResponData.setMessage("Mật khẩu không đúng");
            return new ResponseEntity<>(ResponData, HttpStatus.BAD_REQUEST);
        }else{
            boolean result = LoginServiceimp.changePassword(id, changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
            if(result) {
                ResponData.setSuccess(Boolean.TRUE);
                ResponData.setMessage("Thay đổi mật khẩu thành công");
                return new ResponseEntity<>(ResponData, HttpStatus.OK);
            }else{
                ResponData.setSuccess(Boolean.FALSE);
                ResponData.setMessage("Mật khẩu không đúng");
                return new ResponseEntity<>(ResponData, HttpStatus.BAD_REQUEST);
            }
        }

    }

}
