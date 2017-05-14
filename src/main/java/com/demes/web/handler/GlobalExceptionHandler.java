package com.demes.web.handler;

import com.demes.constants.Routes;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public RedirectView pageNotFound() {
        return new RedirectView(Routes.NOT_FOUND_URI);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public RedirectView missingParameter() {
        return new RedirectView(Routes.ROOT_URI);
    }

}
