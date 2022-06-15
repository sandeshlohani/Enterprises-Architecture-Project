package com.project.eaproject.service;

import com.project.eaproject.dto.OrderDTO;
import com.project.eaproject.dto.ProductDTO;
import com.project.eaproject.model.Order;
import com.project.eaproject.model.OrderProducts;
import com.project.eaproject.model.OrderStatus;

import java.util.List;

public interface OrderService {

    Order addOrder(List<OrderProducts> orderProducts);
    Order updateOrder(List<OrderProducts> orderProducts,String orderId);
    void deleteOrder(long orderId);
    List<Order> getAllOrders();
    void updateOrderStatus(String order_id, OrderStatus status);
    Order getOrderByOrderID(String orderId);
    List<Order> getOrderByUserId(long userID);
    OrderDTO getOrderByProduct(Long orderID);


}
