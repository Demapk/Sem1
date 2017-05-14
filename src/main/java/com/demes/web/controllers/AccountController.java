package com.demes.web.controllers;

import com.demes.constants.Routes;
import com.demes.entity.Order;
import com.demes.service.OrderService;
import com.demes.service.interfaces.IUserService;
import com.demes.util.UserDetailsImpl;
import com.demes.validation.NoCountInWarehousesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller

public class AccountController {
    private static final String ORDER = "order";
    @Autowired
    private IUserService userService;
    @Autowired
    private OrderService orderService;

    @GetMapping(Routes.ACCOUNT_SHOW_URI)
    public String index() {
        return Routes.ACCOUNT_SHOW_VIEW;
    }

    @GetMapping(Routes.ACCOUNT_CONFIGURATION_URI)
    public String showConfig() {
        return Routes.ACCOUNT_CONFIGURATION_VIEW;
    }

    @PostMapping(Routes.ACCOUNT_CONFIGURATION_URI)
    public RedirectView changePassword(@RequestParam("old") String old,
                                       @RequestParam("new") String newP,
                                       @RequestParam("newMatch") String newMatch) {
        boolean isChanged = userService.checkNewPassword(old, newP, newMatch);
        String result = isChanged ? "success" : "error";
        return new RedirectView(Routes.ACCOUNT_CONFIGURATION_URI + "?" + result);
    }

    @GetMapping(Routes.ACCOUNT_BASKET_URI)
    public String showBasket(HttpSession session, ModelMap modelMap) {
        Order order = (Order) session.getAttribute(ORDER);
        if (order != null) {
            modelMap.put("order", order);
            int sum = orderService.calculateOrderSum(order);
            modelMap.put("sum", sum);
        }
        return Routes.ACCOUNT_BASKET_VIEW;
    }

    @PostMapping(Routes.ACCOUNT_BASKET_INCREMENT_URI)
    @ResponseBody
    public int incrementProduct(@RequestParam("product_id") Long productId,
                                @RequestParam("count") int count,
                                HttpSession session) {
        Order order = (Order) session.getAttribute(ORDER);
        return orderService.incrementCountIfPossible(order, productId, count);
    }

    @GetMapping(Routes.ACCOUNT_CHECKOUT_IRI)
    public String checkout(HttpSession session, ModelMap modelMap) {
        Order order = (Order) session.getAttribute(ORDER);
        int sum = orderService.calculateOrderSum(order);
        modelMap.put("sum", sum);
        return Routes.ACCOUNT_CHECKOUT_VIEW;
    }

    @PostMapping(Routes.ACCOUNT_CHECKOUT_IRI)
    public RedirectView doOrder(HttpSession session, @RequestParam("address") String address) {
        Order order = (Order) session.getAttribute(ORDER);
        try {
            orderService.createOrder(order, address);
        } catch (NoCountInWarehousesException e) {
            return new RedirectView(Routes.ACCOUNT_CHECKOUT_IRI + "?error");
        }
        session.removeAttribute(ORDER);
        return new RedirectView(Routes.ACCOUNT_SHOW_URI);
    }

    @GetMapping(Routes.ACCOUNT_ORDERS_URI)
    public String showOrders(Model model) {
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("orders", orderService.findAllByUser(user.getUser()));
        return Routes.ACCOUNT_ORDERS_VIEW;
    }

    @GetMapping(Routes.ACCOUNT_ORDER_ID_URI)
    public String showOrder(@PathVariable("id") Long id, Model model){
        model.addAttribute(ORDER, orderService.findOne(id));
        return Routes.ACCOUNT_ORDER_ID_VIEW;
    }

}
