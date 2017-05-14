package com.demes.constants;

public interface Routes {
    String ROOT_URI = "/";
    String ROOT_VIEW = "home/index";

    String LOGOUT_URI = "/logout";
    String REGISTRATION_URI = "/registration";
    String REGISTRATION_VIEW = "registration/index";

    String REGISTRATION_SUCCESS_URI = REGISTRATION_URI + "?success";
    String REGISTRATION_SUCCESS_VIEW = "registration/success";

    String REGISTRATION_INFO_VIEW = "registration/info";
    String REGISTRATION_RESEND_TOKEN_VIEW = "registration/resend";

    String LOGIN_URI = "/login";
    String LOGIN_VIEW = "login/index";

    String LOGIN_PROCESSING_URI = "/login_processing";

    String LOGIN_INFO_URI = LOGIN_URI + "/info";
    String LOGIN_INFO_VIEW = "login/info";

    String LOGIN_RESET_PASSWORD_VIEW = "login/reset_password";

    String LOGIN_NEW_PASSWORD_URI = LOGIN_URI + "/new_password";
    String LOGIN_NEW_PASSWORD_VIEW = LOGIN_URI + "/new_password";

    String ERROR_URI = "/error";
    String ERROR_VIEW = "status/error";

    String NOT_FOUND_URI = "/404";
    String NOT_FOUND_VIEW = "status/404";

    String ACCESS_DENIED_URI = "/403";
    String ACCESS_DENIED_VIEW = "status/403";

    String SHOP_URI = "/shop";
    String SHOP_VIEW = "shop/index";

    String SHOP_ADD_URI = "/shop/add";

    String SHOP_SHOW_URI = "/shop/{id}";
    String SHOP_SHOW_VIEW = "shop/show";

    String ACCOUNT_SHOW_URI = "/account";
    String ACCOUNT_SHOW_VIEW = "account/index";

    String ACCOUNT_CONFIGURATION_URI = "/account/configuration";
    String ACCOUNT_CONFIGURATION_VIEW = "account/configuration";

    String ACCOUNT_BASKET_URI = "/account/basket";
    String ACCOUNT_BASKET_VIEW = "account/basket";

    String ACCOUNT_BASKET_INCREMENT_URI = "/account/basket/addCount";
    String ACCOUNT_CHECKOUT_IRI = "/account/checkout";
    String ACCOUNT_CHECKOUT_VIEW = "account/checkout";

    String ACCOUNT_ORDERS_URI = "/account/orders";
    String ACCOUNT_ORDERS_VIEW = "account/orders";

    String ACCOUNT_ORDER_ID_URI = "/account/orders/{id}";
    String ACCOUNT_ORDER_ID_VIEW = "account/order";

    String SHOP_ADD_WAREHOUSE_URI = "/admin/addWarehouse";
    String SHOP_ADD_WAREHOUSE_VIEW = "shop/addWarehouse";

    String SHOP_ADD_PRODUCT_URI = "/admin/addProduct";
    String SHOP_ADD_PRODUCT_VIEW = "shop/addProduct";

    String SHOP_ADD_PRODUCT_IN_WAREHOUSE_URI = "/admin/addProductInWarehouse";
    String SHOP_ADD_PRODUCT_IN_WAREHOUSE_VIEW = "shop/addProductInWarehouse";

    String ADMIN_URI = "/admin";
    String ADMIN_VIEW = "admin/index";

    String ADMIN_USERS_URI = "/admin/users";
    String ADMIN_USERS_VIEW = "admin/users";

    String ADMIN_USERS_DELETE_URI = "/admin/users/delete";
    String ADMIN_USERS_ENABLE_URI = "/admin/users/enable";
}
