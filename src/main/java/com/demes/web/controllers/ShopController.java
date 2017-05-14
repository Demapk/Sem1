package com.demes.web.controllers;

import com.demes.constants.Routes;
import com.demes.entity.Order;
import com.demes.entity.Product;
import com.demes.service.OrderService;
import com.demes.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ShopController {
    private static final String ORDER = "order";

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping(Routes.SHOP_URI)
    public String shop(Model model, @RequestParam(value = "category", required = false) String category,
                       @RequestParam(value = "name", required = false) String name) {
        model.addAttribute("categories", productService.findAllCategories());
        List<Product> products = productService.findAllProducts(category, name);
        model.addAttribute("products", products);
        return Routes.SHOP_VIEW;
    }

    @PostMapping(Routes.SHOP_ADD_URI)
    @ResponseBody
    public String addProductToOrder(HttpSession session,
                                    @RequestParam("product_id") Long productId) {
        if (session.getAttribute(ORDER) == null) {
            Order orderInstance = orderService.createOrderInstance(productId);
            session.setAttribute(ORDER, orderInstance);
            return "Товар успешно добавлен";
        }
        return orderService.incrementNewProduct(session.getAttribute(ORDER) ,productId);
    }

    @GetMapping(Routes.SHOP_SHOW_URI)
    public String showProduct(@PathVariable("id") Long id, Model model){
        model.addAttribute("categories", productService.findAllCategories());
        model.addAttribute("product", productService.findOne(id));
        return Routes.SHOP_SHOW_VIEW;
    }
}
