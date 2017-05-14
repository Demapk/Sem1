package com.demes.web.controllers;

import com.demes.constants.Routes;
import com.demes.constants.SecurityConstants;
import com.demes.constants.SessionConstants;
import com.demes.entity.PasswordResetToken;
import com.demes.entity.User;
import com.demes.entity.enums.RoleEnumeration;
import com.demes.service.interfaces.IPasswordResetTokenService;
import com.demes.service.interfaces.ISecureService;
import com.demes.service.interfaces.IUserService;
import com.demes.util.CreateEmailMessageHelper;
import com.demes.util.UrlApplicationHelper;
import com.demes.util.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Collections;
import java.util.UUID;

@Controller
public class LoginController {
    private static final String MESSAGE = "message";
    @Autowired
    private IUserService userService;
    @Autowired
    private IPasswordResetTokenService tokenService;
    @Autowired
    private CreateEmailMessageHelper createEmailMessageHelper;
    @Autowired
    private ISecureService secureService;

    @GetMapping(value = Routes.LOGIN_URI)
    public String index() {
        return Routes.LOGIN_VIEW;
    }

    @GetMapping(value = Routes.LOGIN_URI, params = "reset_password")
    public String resetPassword() {
        return Routes.LOGIN_RESET_PASSWORD_VIEW;
    }

    @PostMapping(value = Routes.LOGIN_URI, params = "reset_password")
    public RedirectView resetPassword(HttpServletRequest request, @RequestParam("email") final String userEmail,
                                      RedirectAttributes redirectAttributes, HttpSession session,
                                      @SessionAttribute(required = false, name = SessionConstants.LAST_RESEND) Long lastResend) {
        if (lastResend != null && Calendar.getInstance().getTime().getTime() - lastResend <= 600000) {
            redirectAttributes.addFlashAttribute(MESSAGE, "Слишком много запросов на сброс пароля. Попробуй 10 минут позднее");
            return new RedirectView(Routes.LOGIN_URI + "?reset_password");
        }
        User user = userService.findUserByEmail(userEmail);
        if (user == null) {
            redirectAttributes.addFlashAttribute(MESSAGE, "Юзер с данным емайлом не найден");
            return new RedirectView(Routes.LOGIN_URI + "?reset_password");
        }
        String token = UUID.randomUUID().toString();
        tokenService.createPasswordResetToken(token, user);
        String appUrl = UrlApplicationHelper.getAppUrl(request);
        try {
            createEmailMessageHelper.sendResetPasswordEmail(appUrl, token, user);
        } catch (Exception e) {
            redirectAttributes.addAttribute(MESSAGE, e.getMessage());
            return new RedirectView(Routes.ERROR_URI);
        }
        redirectAttributes.addFlashAttribute(MESSAGE, "Письмо со сбросом пароля отправлено.");
        session.setAttribute(SessionConstants.LAST_RESEND, Calendar.getInstance().getTime().getTime());
        return new RedirectView(Routes.LOGIN_INFO_URI);
    }

    @GetMapping(Routes.LOGIN_INFO_URI)
    public String showLoginInfo(Model model) {
        if (model.containsAttribute(MESSAGE)) {
            return Routes.LOGIN_INFO_VIEW;
        }
        return "redirect:" + Routes.ROOT_URI;
    }

    @GetMapping(value = Routes.LOGIN_URI, params = {"token", "u", "reset_password"})
    public RedirectView showResetPassword(@RequestParam String token, @RequestParam Long u,
                                          RedirectAttributes redirectAttributes) {
        PasswordResetToken resetToken = tokenService.findPasswordResetToken(token);
        String invalidResult = secureService.checkConfirmResetPasswordToken(resetToken, u);
        if (invalidResult != null) {
            redirectAttributes.addFlashAttribute(MESSAGE, invalidResult);
            return new RedirectView(Routes.LOGIN_INFO_URI);
        }
        User user = resetToken.getUser();
        UserDetails userDetails = new UserDetailsImpl(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,
                null,
                Collections.singletonList(new SimpleGrantedAuthority(SecurityConstants.DEFAULT_ROLE_PREFIX + RoleEnumeration.TEMPORARY_ACCESS.toString())));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView(Routes.LOGIN_NEW_PASSWORD_URI);
    }

    @GetMapping(Routes.LOGIN_NEW_PASSWORD_URI)
    public String showNewPasswordPage() {
        return Routes.LOGIN_NEW_PASSWORD_VIEW;
    }

    @PostMapping(Routes.LOGIN_NEW_PASSWORD_URI)
    public RedirectView saveNewPassword(@RequestParam String password, @RequestParam String matchingPassword,
                                        RedirectAttributes redirectAttributes) {

        if (!password.equals(matchingPassword)) {
            redirectAttributes.addFlashAttribute(MESSAGE, "Пароли не совпадают");
            return new RedirectView(Routes.LOGIN_NEW_PASSWORD_URI);
        }
        UserDetailsImpl userDetails = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User user = userDetails.getUser();
        userService.changeUserPassword(user, password);
        tokenService.deletePasswordResetToken(user);
        userDetails.setUser(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        redirectAttributes.addFlashAttribute(MESSAGE, "Пароль успешно изменен");
        return new RedirectView(Routes.LOGIN_INFO_URI);
    }

}
