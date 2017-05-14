package com.demes.service;

import com.demes.entity.PasswordResetToken;
import com.demes.entity.User;
import com.demes.repository.PasswordResetTokenRepository;
import com.demes.service.interfaces.IPasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PasswordResetTokenService implements IPasswordResetTokenService {
    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PasswordResetToken findPasswordResetToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    @Transactional
    public PasswordResetToken createPasswordResetToken(String token, User user) {
        PasswordResetToken resetToken = tokenRepository.findByUser(user);
        if (resetToken != null) {
            resetToken.setToken(token);
            resetToken.setExpiryDate();
        } else {
            resetToken = new PasswordResetToken(token, user);
        }
        return tokenRepository.save(resetToken);
    }

    @Override
    @Transactional
    public void deletePasswordResetToken(User user) {
        tokenRepository.deleteByUser(user);
    }
}
