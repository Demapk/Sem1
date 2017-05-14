package com.demes.service.interfaces;

import com.demes.entity.PasswordResetToken;
import com.demes.entity.VerificationToken;

public interface ISecureService {
    String checkConfirmRegistrationToken(VerificationToken verificationToken, Long id);

    String checkConfirmResetPasswordToken(PasswordResetToken resetToken, Long id);
}
