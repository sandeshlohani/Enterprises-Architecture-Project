package com.example.userws.repository;

import com.example.userws.domain.Role;
import com.example.userws.enums.RoleType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

  List<Role> findByRoleType(RoleType roleType);
}
