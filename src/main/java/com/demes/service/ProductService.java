package com.demes.service;

import com.demes.entity.Product;
import com.demes.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Product findOne(Long id) {
        return productRepository.findOne(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<String> findAllCategories() {
        return productRepository.findAllDistinctByCategory();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Product> findAllProducts(String category, String name) {
        if (category == null && name == null) {
            return this.findAll();
        }
        if (category != null && name != null) {
            return productRepository.findByCategoryAndNameIgnoreCaseContaining(category, name);
        }
        if (category != null) {
            return productRepository.findByCategory(category);
        }
        return productRepository.findByNameIgnoreCaseContaining(name);
    }
}
