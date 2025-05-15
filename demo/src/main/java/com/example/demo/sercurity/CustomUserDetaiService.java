package com.example.demo.sercurity;


import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetaiService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println(email);
        Users users = userRepository.findByEmail(email);
        if (users == null) {
            throw new UsernameNotFoundException("Users not found");
        }
        String roleName = users.getRoles().getRoleName();//
        System.out.println(users.getEmail()+"ffdfd");
        return new User(email,users.getPassword(), List.of(new SimpleGrantedAuthority(roleName)));//
    }
}
