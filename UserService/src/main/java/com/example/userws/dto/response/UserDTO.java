package com.example.userws.dto.response;

import com.example.userws.domain.Role;
import com.example.userws.enums.RoleType;
import com.example.userws.enums.Status;
import java.time.Instant;
import java.util.List;
import lombok.Data;

@Data
public class UserDTO {

  private Long id;
  private String userId;
  private String name;
  private String email;
  private RoleType roleType;
  private List<Role> roles;
  private List<OrderDTO> orderDTOS;
}
