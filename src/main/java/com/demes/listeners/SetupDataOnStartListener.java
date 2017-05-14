package com.demes.listeners;


import com.demes.entity.*;
import com.demes.entity.enums.RoleEnumeration;
import com.demes.repository.ProductInWarehouseRepository;
import com.demes.repository.ProductRepository;
import com.demes.repository.RoleRepository;
import com.demes.repository.WarehouseRepository;
import com.demes.service.ProductService;
import com.demes.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Component
public class SetupDataOnStartListener implements ApplicationListener<ContextRefreshedEvent> {
    private boolean isAlreadySetup = false;
    @Autowired
    private IUserService userService;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductInWarehouseRepository productInWarehouseRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (isAlreadySetup) {
            return;
        }
        Role admin = roleRepository.save(new Role(RoleEnumeration.ADMIN));
        Role customer = roleRepository.save(new Role(RoleEnumeration.CUSTOMER));
        User user = User.builder().email("astanafed@mail.ru").username("Astana").enabled(true).roles(Collections.singleton(admin)).password("fedastana1").build();
        userService.save(user, true);
        user = User.builder().email("demes@gmail.com").username("Demes").enabled(true).roles(Collections.singleton(customer)).password("Qwerty123").build();
        userService.save(user, true);
        Product product = Product.builder().name("Шины").category("Расходники").price(3000).description(UUID.randomUUID().toString()).build();
        Warehouse warehouse = Warehouse.builder().city("Казань").address("Ул. Завойского").build();
        ProductInWarehouse productInWarehouse = ProductInWarehouse.builder().count(20).product(product).warehouse(warehouse).build();
        productRepository.save(product);
        warehouseRepository.save(warehouse);
        productInWarehouseRepository.save(productInWarehouse);
        product = Product.builder().name("Диоды").category("Оптика").price(2500).description(UUID.randomUUID().toString()).build();
        warehouse = Warehouse.builder().city("Москва").address("Ул. Завойского").build();
        productInWarehouse = ProductInWarehouse.builder().count(20).product(product).warehouse(warehouse).build();
        productRepository.save(product);
        warehouseRepository.save(warehouse);
        productInWarehouseRepository.save(productInWarehouse);
        isAlreadySetup = true;
    }
}
