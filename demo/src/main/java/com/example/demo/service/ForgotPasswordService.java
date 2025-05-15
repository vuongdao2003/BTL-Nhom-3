package com.example.demo.service;

import com.example.demo.service.imp.ForgotPasswordServiceimp;

public class ForgotPasswordService implements ForgotPasswordServiceimp {
    @Override
    public boolean sendResetLink(String email) {
        return false;
    }

    @Override
    public boolean resetPassword(String token, String newPassword, String confirmPassword) {
        return false;
    }
}
