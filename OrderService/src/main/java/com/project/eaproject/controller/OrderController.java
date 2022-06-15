package com.project.eaproject.controller;

import com.project.eaproject.dto.OrderClientDTO;
import com.project.eaproject.dto.OrderDTO;
import com.project.eaproject.helper.OrderMapper;
import com.project.eaproject.helper.StatusMapper;
import com.project.eaproject.model.Order;
import com.project.eaproject.model.OrderProducts;
import com.project.eaproject.model.OrderStatus;
import com.project.eaproject.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders/")
public class OrderController {


    private final OrderService orderService;
    private final ModelMapper modelMapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order addOrder(@RequestBody OrderMapper orderMapper) {
        return orderService.addOrder(orderMapper.getOrderProducts());
    }

    @PutMapping("/{orderId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Order updateOrder(@RequestBody List<OrderProducts> orderProducts, @PathVariable("orderId") String orderId) {
        return orderService.updateOrder(orderProducts, orderId);
    }

    @GetMapping("/{orderId}")
    public Order order(@PathVariable String orderId) {
        return orderService.getOrderByOrderID(orderId);
    }

    @PostMapping("/update/status")
    public void updateStatus(@RequestBody StatusMapper statusMapper) {


        if (OrderStatus.SHIPPED.toString().equals(statusMapper.getStatus())) {
            orderService.updateOrderStatus(statusMapper.getOrder_id(), OrderStatus.SHIPPED);
        } else if (OrderStatus.CANCELLED.toString().equals(statusMapper.getStatus())) {
            orderService.updateOrderStatus(statusMapper.getOrder_id(), OrderStatus.CANCELLED);
        } else if (OrderStatus.DELIVERED.toString().equals(statusMapper.getStatus())) {
            orderService.updateOrderStatus(statusMapper.getOrder_id(), OrderStatus.DELIVERED);
        } else {
            orderService.updateOrderStatus(statusMapper.getOrder_id(), OrderStatus.PLACED);
        }


    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/user/{user_id}")
    public List<OrderClientDTO> getOrderByUserId(@PathVariable("user_id") long user_id) {
        return orderService.getOrderByUserId(user_id).stream().map(order -> modelMapper.map(order, OrderClientDTO.class)).collect(Collectors.toList());
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable String orderId) {
        Order order = orderService.getOrderByOrderID(orderId);
        orderService.deleteOrder(order.getId());
    }

    @GetMapping("/products/{orderId}")
    public OrderDTO getOrderByProducts(@PathVariable Long orderId){
        return orderService.getOrderByProduct(orderId);
    }

}
