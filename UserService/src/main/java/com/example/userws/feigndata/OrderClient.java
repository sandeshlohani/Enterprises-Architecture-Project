package com.example.userws.feigndata;

import com.example.userws.dto.response.OrderDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.ArrayList;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order")
public interface OrderClient {

  @GetMapping("orders/user/{user_id}")
  @Retry(name = "order")
  @CircuitBreaker(name = "order", fallbackMethod = "getOrdersFallBack")
  List<OrderDTO> getOrders(@PathVariable Long user_id);

  default List<OrderDTO> getOrdersFallBack(Long id, Throwable exception) {
    return new ArrayList<>();
  }
}
