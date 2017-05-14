package com.demes.repository;

import com.demes.entity.Order;
import com.demes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}
