package com.demes.util;

import com.demes.entity.User;
import com.demes.entity.VerificationToken;
import com.demes.event.OnRegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class CreateEmailMessageHelper {
    @Autowired
    private Environment environment;
    @Autowired
    private MailSender mailSender;

    private void sendEmail(String subject, String text,
                           String from, String to) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        mailSender.send(simpleMailMessage);
    }

    public void resendVerificationTokenEmail(String appUrl, VerificationToken newToken, User user) {
        String subject = "Подтверждение регистрации";
        String from = environment.getProperty("smtp.username");
        String to = user.getEmail();
        String confirmationUrl =
                appUrl + "/registration?token=" + newToken.getToken() + "&u=" + user.getId();
        String text = "Ссылка на подтверждение регистрации" + "\r\n" + confirmationUrl;
        sendEmail(subject, text, from, to);
    }

    public void sendVerificationTokenEmail(OnRegistrationCompleteEvent event, User user, String token) {
        String subject = "Подтверждение регистрации";
        String from = environment.getProperty("smtp.username");
        String to = user.getEmail();
        String confirmationUrl =
                event.getAppUrl() + "/registration?token=" + token + "&u=" + user.getId();
        String text = "Ссылка на подтверждение регистрации" + "\r\n" + confirmationUrl;
        sendEmail(subject, text, from, to);
    }

    public void sendResetPasswordEmail(String appUrl, String token, User user) {
        String subject = "Сброс пароля: Автозапчасти";
        String from = environment.getProperty("smtp.username");
        String to = user.getEmail();
        String confirmationUrl =
                appUrl + "/login?reset_password&token=" + token + "&u=" + user.getId();
        String text = "Проследуйте по ссылке ниже, чтобы сбросить свой пароль" + "\r\n" + confirmationUrl;
        sendEmail(subject, text, from, to);
    }
}
