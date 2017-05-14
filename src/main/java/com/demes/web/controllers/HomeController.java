package com.demes.web.controllers;

import com.demes.constants.Routes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {Routes.ROOT_URI})
public class HomeController {

    @GetMapping
    public String index() {
        return Routes.ROOT_VIEW;
    }

}
