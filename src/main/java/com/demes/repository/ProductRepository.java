package com.demes.repository;

import com.demes.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByName(String name);

    @Query(value = "select distinct category from Product")
    List<String> findAllDistinctByCategory();
    
    List<Product> findByCategory(String category);
    
    List<Product> findByCategoryAndNameIgnoreCaseContaining(String category, String name);

    List<Product> findByNameIgnoreCaseContaining(String name);
    
}
