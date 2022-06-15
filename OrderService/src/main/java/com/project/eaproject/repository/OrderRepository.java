package com.project.eaproject.repository;

import com.project.eaproject.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("SELECT o from ProductOrder o where o.order_id=:orderId")
    Order findByOrder_id(String orderId);
    List<Order> findByUserId(Long uid);
}
