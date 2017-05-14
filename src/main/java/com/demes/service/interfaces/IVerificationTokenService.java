package com.demes.service.interfaces;


import com.demes.entity.User;
import com.demes.entity.VerificationToken;

public interface IVerificationTokenService {
    VerificationToken createNewVerificationToken(String existingToken);

    void deleteVerificationToken(VerificationToken verificationToken);

    VerificationToken createVerificationToken(User user, String token);

    VerificationToken findVerificationToken(String token);
}
