package com.example.userws.service.impl;

import com.example.userws.domain.Role;
import com.example.userws.domain.User;
import com.example.userws.dto.response.OrderDTO;
import com.example.userws.dto.response.UserDTO;
import com.example.userws.enums.Status;
import com.example.userws.exception.UnprocessableException;
import com.example.userws.feigndata.OrderClient;
import com.example.userws.repository.RoleRepo;
import com.example.userws.repository.UserRepo;
import com.example.userws.service.UserService;
import feign.FeignException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.naming.CannotProceedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for user
 *
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepo userRepo;
  private final RoleRepo roleRepo;
  private final PasswordEncoder passwordEncoder;
  private final OrderClient orderClient;
  private final ModelMapper modelMapper;

  /**
   * Persist the data in the database
   *
   * @param user to save
   */
  @Override
  public void save(User user) throws CannotProceedException {
    if (user.getRoleType() != null) {

      if (userRepo.existsByEmail(user.getEmail())) {
        throw new UnprocessableException(String.format("User with email %s already exist.", user.getEmail()));
      }

      List<Role> roleList = roleRepo.findByRoleType(user.getRoleType());
      user.setRoles(roleList);
      user.setStatus(Status.CREATED);
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      user.setCreatedDate(Instant.now());
      userRepo.save(user);
    } else {
      throw new CannotProceedException("roleType is missing");
    }
  }

  /**
   * Delete the specific object by id
   *
   * @param id of object
   */
  @Override
  public void deleteById(long id) {
    userRepo.deleteById(id);
  }

  /**
   * @return list of all objects
   */
  @Override
  public List<User> getAll() {
    return userRepo.findAll();
  }

  /**
   * Find the object by id
   *
   * @param id of object
   * @return T
   */
  @Override
  public User findById(long id) {
    Optional<User> user = userRepo.findById(id);
    return user.orElse(null);
  }


  /**
   * find UserDetails by username
   *
   * @param username in our case is email
   * @return UserDetails to verify user
   * @throws UsernameNotFoundException
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepo.findByEmail(username);
    if (user != null) {

      List<String> roles = user.getRoles().stream().map(r -> r.getRoleType().name()).collect(Collectors.toList());
      return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
          AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", roles)));
    } else {
      throw new UsernameNotFoundException(username);
    }
  }

  @Override
  public User getActiveUser(String email) {
    return userRepo.findByEmail(email);
  }

  @Override
  public UserDTO getUserDetailsWithOrders(Long userId) {
    User user = userRepo.findById(userId).get();
    UserDTO userDTO = modelMapper.map(user, UserDTO.class);
    List<OrderDTO> orderDTOS = new ArrayList<>();
    try {
      orderDTOS = orderClient.getOrders(userId);
    } catch (FeignException exception) {
      log.error(exception.getLocalizedMessage());
    }
    userDTO.setOrderDTOS(orderDTOS);
    return userDTO;
  }
}
