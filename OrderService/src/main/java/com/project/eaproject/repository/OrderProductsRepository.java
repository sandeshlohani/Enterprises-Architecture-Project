package com.project.eaproject.repository;

import com.project.eaproject.model.OrderProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductsRepository extends JpaRepository<OrderProducts,Long> {
}
