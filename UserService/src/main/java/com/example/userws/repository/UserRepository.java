package com.example.userws.repository;

import com.example.userws.domain.User;
import com.example.userws.enums.RoleType;
import com.example.userws.enums.Status;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findByRoleTypeAndStatus(RoleType roleType, Status status);

  List<User> findByRoleType(RoleType roleType);

  User findByIdAndRoleType(Long id, RoleType roleType);

  User findByEmail(String email);

}
