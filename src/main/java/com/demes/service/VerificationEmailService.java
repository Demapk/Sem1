package com.demes.service;

import com.demes.entity.User;
import com.demes.entity.VerificationToken;
import com.demes.repository.VerificationTokenRepository;
import com.demes.service.interfaces.IVerificationTokenService;
import com.demes.validation.TokenNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class VerificationEmailService implements IVerificationTokenService {
    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Override
    public void deleteVerificationToken(VerificationToken verificationToken) {
        tokenRepository.delete(verificationToken);
    }

    @Override
    public VerificationToken createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        return tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken createNewVerificationToken(String existingToken) {
        VerificationToken verificationToken = findVerificationToken(existingToken);
        if (verificationToken == null) {
            throw new TokenNotFoundException();
        }
        verificationToken.updateToken(UUID.randomUUID().toString());
        return tokenRepository.save(verificationToken);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public VerificationToken findVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
