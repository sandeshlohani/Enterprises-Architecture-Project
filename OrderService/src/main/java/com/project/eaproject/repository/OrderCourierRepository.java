package com.project.eaproject.repository;

import com.project.eaproject.model.OrderCourierStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCourierRepository extends JpaRepository<OrderCourierStatus,Long> {
}
