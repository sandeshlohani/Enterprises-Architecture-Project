package com.project.eaproject.service;

import com.project.eaproject.dto.OrderDTO;
import com.project.eaproject.dto.ProductDTO;
import com.project.eaproject.dto.User;
import com.project.eaproject.feigndata.ProductClient;
import com.project.eaproject.feigndata.UserClient;
import com.project.eaproject.model.Order;
import com.project.eaproject.model.OrderCourierStatus;
import com.project.eaproject.model.OrderProducts;
import com.project.eaproject.model.OrderStatus;
import com.project.eaproject.repository.OrderCourierRepository;
import com.project.eaproject.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderCourierRepository orderCourierRepository;
    private final ProductClient productClient;
    private final ModelMapper modelMapper;
    private final UserClient userClient;

    @Override
    public Order addOrder(List<OrderProducts> orderProducts) {

        Random rd = new Random();
        String orderId = "EAS"+(rd.nextLong());

        OrderCourierStatus orderCourierStatus = new OrderCourierStatus(OrderStatus.PLACED,LocalDate.now());
        orderCourierRepository.save(orderCourierStatus);

        Order o = new Order(orderId,LocalDate.now(),userClient.getActiveUser().getId());
        o.getOrderProducts().addAll(orderProducts);
        o.setOrderCourierStatus(orderCourierStatus);

        return orderRepository.save(o);
    }

    @Override
    public Order updateOrder(List<OrderProducts> orderProducts,String orderId) {
        Order order = this.getOrderByOrderID(orderId);
        order.getOrderProducts().removeAll(order.getOrderProducts());
        order.getOrderProducts().addAll(orderProducts);
        return order;
    }


    @Override
    public void deleteOrder(long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findByUserId(userClient.getActiveUser().getId());
    }

    @Override
    public void updateOrderStatus(String order_id, OrderStatus status) {
        Order order = this.getOrderByOrderID(order_id);
        order.getOrderCourierStatus().setStatus(status);
        order.getOrderCourierStatus().setStatusDate(LocalDate.now());
    }

    @Override
    public Order getOrderByOrderID(String orderId) {
        return orderRepository.findByOrder_id(orderId);
    }

    @Override
    public List<Order> getOrderByUserId(long userID) {
        return orderRepository.findByUserId(userID);
    }

    @Override
    public OrderDTO getOrderByProduct(Long orderID) {
        Order order = orderRepository.findById(orderID).get();
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        List<ProductDTO> productDTOS = new ArrayList<>();
        order.getOrderProducts().forEach(orderProducts -> {
            productDTOS.add(productClient.getProducts(orderProducts.getProduct_id()));
        });
        orderDTO.setProductDTOS(productDTOS);
        return orderDTO;
    }
}
