package com.demes.service;

import com.demes.entity.PasswordResetToken;
import com.demes.entity.User;
import com.demes.entity.VerificationToken;
import com.demes.service.interfaces.ISecureService;
import com.demes.service.interfaces.IUserService;
import com.demes.service.interfaces.IVerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class SecureService implements ISecureService {
    @Autowired
    private IUserService userService;
    @Autowired
    private IVerificationTokenService tokenService;

    @Override
    public String checkConfirmRegistrationToken(VerificationToken verificationToken, Long id) {
        User user;
        if (verificationToken == null || !(user = verificationToken.getUser()).getId().equals(id)) {
            return "Невалидный токен";
        }
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "Время действия токена истекло";
        }
        user.setEnabled(true);
        userService.save(user, false);
        tokenService.deleteVerificationToken(verificationToken);
        return null;
    }

    @Override
    public String checkConfirmResetPasswordToken(PasswordResetToken resetToken, Long id) {
        if (resetToken == null || !resetToken.getUser().getId().equals(id)) {
            return "Невалидный токен";
        }
        Calendar calendar = Calendar.getInstance();
        if (resetToken.getExpiryDate().getTime() - calendar.getTime().getTime() <= 0) {
            return "Время действие токена истекло";
        }
        return null;
    }

}
