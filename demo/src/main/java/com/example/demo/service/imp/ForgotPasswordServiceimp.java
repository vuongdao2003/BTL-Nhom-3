package com.example.demo.service.imp;

public interface ForgotPasswordServiceimp {
    boolean sendResetLink(String email);
    boolean resetPassword(String token, String newPassword, String confirmPassword);

    }
