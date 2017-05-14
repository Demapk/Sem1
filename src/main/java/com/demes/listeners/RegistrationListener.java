package com.demes.listeners;


import com.demes.constants.SessionConstants;
import com.demes.entity.User;
import com.demes.event.OnRegistrationCompleteEvent;
import com.demes.service.interfaces.IVerificationTokenService;
import com.demes.util.CreateEmailMessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private HttpSession session;

    @Autowired
    private IVerificationTokenService tokenService;

    @Autowired
    private CreateEmailMessageHelper emailMessage;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        tokenService.createVerificationToken(user, token);
        emailMessage.sendVerificationTokenEmail(event, user, token);
        session.setAttribute(SessionConstants.EXISTING_TOKEN, token);
    }
}
