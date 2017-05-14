package com.demes.web.controllers;


import com.demes.constants.Routes;
import com.demes.entity.Product;
import com.demes.entity.ProductInWarehouse;
import com.demes.entity.Warehouse;
import com.demes.service.ProductInWarehouseService;
import com.demes.service.ProductService;
import com.demes.service.WarehouseService;
import com.demes.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AdminController {
    @Autowired
    private ProductService productService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private ProductInWarehouseService productInWarehouseService;
    @Autowired
    private IUserService iUserService;

    @GetMapping(Routes.ADMIN_URI)
    public String getAdmin() {
        return Routes.ADMIN_VIEW;
    }

    @GetMapping(Routes.ADMIN_USERS_URI)
    public String getUsers(Model model){
        model.addAttribute("users", iUserService.findAllNonAdminUsers());
        return Routes.ADMIN_USERS_VIEW;
    }

    @GetMapping(Routes.SHOP_ADD_PRODUCT_URI)
    public String showAddProduct() {
        return Routes.SHOP_ADD_PRODUCT_VIEW;
    }


    @GetMapping(Routes.SHOP_ADD_PRODUCT_IN_WAREHOUSE_URI)
    public String showAddProductInWarehouse(Model model) {
        model.addAttribute("warehouses", warehouseService.findAll());
        model.addAttribute("products", productService.findAll());
        return Routes.SHOP_ADD_PRODUCT_IN_WAREHOUSE_VIEW;
    }

    @GetMapping(Routes.SHOP_ADD_WAREHOUSE_URI)
    public String showAddWarehouse() {
        return Routes.SHOP_ADD_WAREHOUSE_VIEW;
    }

    @PostMapping(Routes.SHOP_ADD_PRODUCT_URI)
    public RedirectView addProduct(@RequestParam("name") String name,
                                   @RequestParam("description") String description,
                                   @RequestParam("price") int price,
                                   @RequestParam("category") String category) {
        if (productService.findByName(name) != null) {
            return new RedirectView(Routes.SHOP_ADD_PRODUCT_URI + "?error");
        }
        Product product = Product.builder()
                .description(description)
                .name(name)
                .price(price)
                .category(category)
                .build();
        productService.save(product);
        return new RedirectView(Routes.SHOP_ADD_PRODUCT_URI + "?success");
    }

    @PostMapping(Routes.SHOP_ADD_WAREHOUSE_URI)
    public RedirectView addWarehouse(@RequestParam("city") String city, @RequestParam("address") String address) {
        if (warehouseService.findByCityAndByAddress(city, address) != null) {
            return new RedirectView(Routes.SHOP_ADD_WAREHOUSE_URI + "?error");
        }
        Warehouse warehouse = Warehouse.builder().city(city).address(address).build();
        warehouseService.save(warehouse);
        return new RedirectView(Routes.SHOP_ADD_WAREHOUSE_URI + "?success");
    }

    @PostMapping(Routes.SHOP_ADD_PRODUCT_IN_WAREHOUSE_URI)
    public RedirectView addProductInWarehouse(@RequestParam("warehouse") Long warehouse,
                                              @RequestParam("product") Long product,
                                              @RequestParam("count") int count) {
        Warehouse warehouse1;
        Product product1;
        if ((product1 = productService.findOne(product)) == null
                || (warehouse1 = warehouseService.findOne(warehouse)) == null) {
            return new RedirectView(Routes.SHOP_ADD_PRODUCT_IN_WAREHOUSE_VIEW + "?error");
        }
        ProductInWarehouse productInWarehouse = ProductInWarehouse.builder()
                .warehouse(warehouse1)
                .product(product1)
                .count(count)
                .build();
        productInWarehouseService.save(productInWarehouse);
        return new RedirectView(Routes.SHOP_ADD_PRODUCT_IN_WAREHOUSE_URI + "?success");
    }
    @PostMapping(Routes.ADMIN_USERS_DELETE_URI)
    @ResponseBody
    public String deleteUser(@RequestParam("user_id") Long userId){
        iUserService.deleteUser(userId);
        return "OK";
    }

    @PostMapping(Routes.ADMIN_USERS_ENABLE_URI)
    @ResponseBody
    public String enableUser(@RequestParam("user_id") Long userId){
        return iUserService.enableOrDisableUser(userId);
    }
}
