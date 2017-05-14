package com.demes.repository;

import com.demes.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Warehouse findByCityAndAddress(String city, String address);
}
