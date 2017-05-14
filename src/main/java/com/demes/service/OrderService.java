package com.demes.service;

import com.demes.entity.*;
import com.demes.entity.enums.OrderStatus;
import com.demes.repository.OrderRepository;
import com.demes.repository.ProductInWarehouseRepository;
import com.demes.repository.ProductRepository;
import com.demes.util.UserDetailsImpl;
import com.demes.validation.NoCountInWarehousesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductInWarehouseRepository productInWarehouseRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Order createOrderInstance(Long productId) {
        Order order = Order.builder().status(OrderStatus.FORMED).build();
        Product product = productRepository.findOne(productId);
        List<ProductInOrder> productInOrders = new ArrayList<>();
        ProductInOrder productInOrder = ProductInOrder.builder().order(order).product(product)
                .count(1).build();
        productInOrders.add(productInOrder);
        order.setProductInOrders(productInOrders);
        return order;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Order> findAllByUser(User user){
         return orderRepository.findAllByUser(user);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public String incrementNewProduct(Object attribute, Long productId) {
        Order order = (Order) attribute;
        for (ProductInOrder productInOrder : order.getProductInOrders()) {
            if (Objects.equals(productInOrder.getProduct().getId(), productId)) {
                return "Товар уже есть в корзине.";
            }
        }
        Product product = productRepository.findOne(productId);
        order.getProductInOrders().add(ProductInOrder.builder().order(order).count(1).product(product).build());
        return "Товар успешно добавлен";
    }

    public int calculateOrderSum(Order order) {
        int sum = 0;
        for (ProductInOrder productInOrder : order.getProductInOrders()) {
            sum += productInOrder.getCount() * productInOrder.getProduct().getPrice();
        }
        return sum;
    }

    @Transactional
    public int incrementCountIfPossible(Order order, Long productId, int count) {
        int countToReturn = 0;
        for (ProductInOrder productInOrder : order.getProductInOrders()) {
            if (productId.equals(productInOrder.getProduct().getId())) {
                int sumInWarehouses = 0;
                for (ProductInWarehouse piw : productInOrder.getProduct().getProductInWarehouses()) {
                    sumInWarehouses += piw.getCount();
                }
                if (count <= sumInWarehouses) {
                    productInOrder.setCount(count);
                    countToReturn = count;
                } else {
                    productInOrder.setCount(sumInWarehouses);
                    countToReturn = sumInWarehouses;
                }
                break;
            }
        }
        return countToReturn;
    }

    @Transactional
    public void createOrder(Order order, String address) {
        for (ProductInOrder productInOrder : order.getProductInOrders()) {
            int count = productInOrder.getCount();
            int warehouseCount = 0;
            for (ProductInWarehouse piw : productInOrder.getProduct().getProductInWarehouses()) {
                warehouseCount += piw.getCount();
                if (warehouseCount >= count) break;
            }
            if (count > warehouseCount) {
                throw new NoCountInWarehousesException();
            }
            order.setSum(order.getSum() + count * productInOrder.getProduct().getPrice());
            for (ProductInWarehouse piw : productInOrder.getProduct().getProductInWarehouses()) {
                int count_in_war = piw.getCount();
                if (count <= count_in_war) {
                    piw.setCount(count_in_war - count);
                    break;
                }
                count-=count_in_war;
                piw.setCount(0);
                productInWarehouseRepository.save(piw);
            }
        }
        order.setStatus(OrderStatus.SEARS);
        order.setAddress(address);
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        order.setUser(user.getUser());
        orderRepository.save(order);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Order findOne(Long id){
        Order one = orderRepository.findOne(id);
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!one.getUser().getId().equals(user.getUser().getId())){
            throw new SecurityException();
        }
        return one;
    }
}
