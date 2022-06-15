package com.example.userws.controller;


import com.example.userws.domain.User;
import com.example.userws.dto.response.UserDTO;
import com.example.userws.service.UserService;
import java.security.Principal;
import java.util.List;
import javax.naming.CannotProceedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public void save(@RequestBody User p) throws CannotProceedException {
    userService.save(p);
  }

  @GetMapping("/active")
  public User get(Principal principal) {
    return userService.getActiveUser(principal.getName());
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getUserWithOrders(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getUserDetailsWithOrders(id));
  }

  @GetMapping
  public ResponseEntity<List<User>> getAll() {
    return ResponseEntity.ok(userService.getAll());
  }

}
