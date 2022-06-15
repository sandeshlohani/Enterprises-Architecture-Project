package com.example.userws.service;


import com.example.userws.domain.User;
import com.example.userws.dto.response.UserDTO;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends BaseService<User>, UserDetailsService {

  User getActiveUser(String email);

  UserDTO getUserDetailsWithOrders(Long userId);

}
