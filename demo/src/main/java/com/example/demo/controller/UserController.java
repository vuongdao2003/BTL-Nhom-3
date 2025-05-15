package com.example.demo.controller;

import com.example.demo.dto.UseridDTO;
import com.example.demo.payload.ResponData;
import com.example.demo.payload.request.UserRequest;
import com.example.demo.service.imp.UserServiceimp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceimp userService;
    @GetMapping("")
    public ResponseEntity<?> getAllUser(){

        return new ResponseEntity<>(userService.getAlluser(), HttpStatus.OK);
    }

    @GetMapping("/getuserbyid/{id}")
    public ResponseEntity<?> getUserbyid(@PathVariable int id){
        List<UseridDTO> useridDTO = userService.getuserbyid(id);
        return new ResponseEntity<>(useridDTO, HttpStatus.OK);

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody UserRequest userRequest){
        ResponData responData = new ResponData();
        UseridDTO userDTO = new UseridDTO();
        userDTO.setId(id);
        userDTO.setEmail(userRequest.getEmail());
        userDTO.setPhone(userRequest.getPhone());
        String result = userService.updateUser(userDTO);
        if(result.equals("Cập nhật thành công")){
            responData.setMessage(result);
            return new ResponseEntity<>(responData, HttpStatus.OK);
        }else {
            responData.setMessage(result);
            responData.setSuccess(false);
            return new ResponseEntity<>(responData, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
        ResponData responData = new ResponData();
        boolean isDeleted = userService.deleteuser(id);
        if(isDeleted){
            responData.setMessage("success");
            return new ResponseEntity<>(responData, HttpStatus.OK);
        }else {
            responData.setMessage("fail");
            responData.setSuccess(isDeleted);
            return new ResponseEntity<>(responData, HttpStatus.BAD_REQUEST);

        }
    }
}
