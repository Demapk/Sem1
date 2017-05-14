package com.demes.service;

import com.demes.entity.Warehouse;
import com.demes.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WarehouseService {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Warehouse findByCityAndByAddress(String city, String address) {
        return warehouseRepository.findByCityAndAddress(city, address);
    }

    @Transactional
    public Warehouse save(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Warehouse findOne(Long id) {
        return warehouseRepository.findOne(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }
}
