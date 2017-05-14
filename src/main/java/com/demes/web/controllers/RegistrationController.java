package com.demes.web.controllers;

import com.demes.constants.Routes;
import com.demes.constants.SessionConstants;
import com.demes.entity.User;
import com.demes.entity.VerificationToken;
import com.demes.event.OnRegistrationCompleteEvent;
import com.demes.service.interfaces.ISecureService;
import com.demes.service.interfaces.IUserService;
import com.demes.service.interfaces.IVerificationTokenService;
import com.demes.util.CreateEmailMessageHelper;
import com.demes.util.UrlApplicationHelper;
import com.demes.validation.TokenNotFoundException;
import com.demes.web.dto.UserDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Calendar;

@Controller
@Log4j2
public class RegistrationController {
    private static final String MESSAGE = "message";
    private static final String modelName = "user";
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private IUserService userService;
    @Autowired
    private IVerificationTokenService tokenService;
    @Autowired
    private ISecureService secureService;
    @Autowired
    private CreateEmailMessageHelper createEmailMessageHelper;

    @GetMapping(Routes.REGISTRATION_URI)
    public String index(ModelMap modelMap) {
        if (!modelMap.containsAttribute(modelName)) {
            modelMap.addAttribute(modelName, new UserDto());
        }
        return Routes.REGISTRATION_VIEW;
    }

    @PostMapping(Routes.REGISTRATION_URI)
    public RedirectView registerUser(@Valid @ModelAttribute(modelName) UserDto userDto,
                                     BindingResult result, RedirectAttributes redirectAttributes,
                                     HttpServletRequest request) {
        redirectAttributes.addFlashAttribute(modelName, userDto);
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult." + modelName, result);
        if (result.hasErrors()) {
            return new RedirectView(Routes.REGISTRATION_URI);
        }
        User registered = userService.createUserAccount(userDto);
        if (registered == null) {
            result.rejectValue("email", null, "Email существует");
            result.rejectValue("username", null, "Имя существует");
            return new RedirectView(Routes.REGISTRATION_URI);
        }
        String appUrl = UrlApplicationHelper.getAppUrl(request);
        try {
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, appUrl));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(MESSAGE, e.getMessage());
            return new RedirectView(Routes.ERROR_URI);
        }
        return new RedirectView(Routes.REGISTRATION_SUCCESS_URI);
    }

    @GetMapping(value = Routes.REGISTRATION_URI, params = "success")
    public String successRegistration(ModelMap modelMap) {
        if (modelMap.containsAttribute(modelName)) {
            return Routes.REGISTRATION_SUCCESS_VIEW;
        }
        return "redirect:" + Routes.ROOT_URI;
    }

    @GetMapping(value = Routes.REGISTRATION_URI, params = {"token", "u"})
    public String confirmRegistrationByToken(@RequestParam String token,
                                             @RequestParam Long u,
                                             Model model,
                                             HttpSession session) {
        VerificationToken verificationToken = tokenService.findVerificationToken(token);
        String invalidResult = secureService.checkConfirmRegistrationToken(verificationToken, u);
        if (invalidResult != null) {
            model.addAttribute(MESSAGE, invalidResult);
            return Routes.REGISTRATION_INFO_VIEW;
        }
        session.removeAttribute(SessionConstants.LAST_RESEND);
        session.removeAttribute(SessionConstants.EXISTING_TOKEN);
        return "redirect:" + Routes.LOGIN_URI;
    }

    @GetMapping(value = Routes.REGISTRATION_URI, params = "resend_token")
    public String resendRegistrationToken(final @SessionAttribute(value = SessionConstants.EXISTING_TOKEN, required = false) String existingToken,
                                          HttpServletRequest request, HttpSession session,
                                          @SessionAttribute(required = false) Long lastResend,
                                          Model model) {
        if (existingToken != null) {
            Calendar cal = Calendar.getInstance();
            if (lastResend != null && cal.getTime().getTime() - lastResend <= 300000) {
                model.addAttribute("tooManyResend", true);
                return Routes.REGISTRATION_RESEND_TOKEN_VIEW;
            }
            final VerificationToken newToken;
            try {
                newToken = tokenService.createNewVerificationToken(existingToken);
            } catch (TokenNotFoundException e) {
                return "redirect:" + Routes.ERROR_URI;
            }

            String appUrl = UrlApplicationHelper.getAppUrl(request);
            try {
                createEmailMessageHelper.resendVerificationTokenEmail(appUrl, newToken, newToken.getUser());
                session.setAttribute(SessionConstants.EXISTING_TOKEN, newToken.getToken());
            } catch (MailException e) {
                log.debug("Mail Exception", e);
                return "redirect:" + Routes.ERROR_URI;
            } catch (Exception e) {
                log.debug(e.getLocalizedMessage(), e);
                return "redirect:" + Routes.ERROR_URI;
            }
            session.setAttribute(SessionConstants.LAST_RESEND, Calendar.getInstance().getTime().getTime());
            return Routes.REGISTRATION_RESEND_TOKEN_VIEW;
        }
        return "redirect:" + Routes.ROOT_URI;
    }

}
