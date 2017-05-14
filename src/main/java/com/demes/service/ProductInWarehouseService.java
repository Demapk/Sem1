package com.demes.service;

import com.demes.entity.ProductInWarehouse;
import com.demes.repository.ProductInWarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductInWarehouseService {
    @Autowired
    private ProductInWarehouseRepository productInWarehouseRepository;

    @Transactional
    public ProductInWarehouse save(ProductInWarehouse productInWarehouse) {
        return productInWarehouseRepository.save(productInWarehouse);
    }
}
