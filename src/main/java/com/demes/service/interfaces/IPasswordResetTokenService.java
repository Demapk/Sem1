package com.demes.service.interfaces;


import com.demes.entity.PasswordResetToken;
import com.demes.entity.User;

public interface IPasswordResetTokenService {
    PasswordResetToken findPasswordResetToken(String token);

    PasswordResetToken createPasswordResetToken(String token, User user);

    void deletePasswordResetToken(User user);
}
