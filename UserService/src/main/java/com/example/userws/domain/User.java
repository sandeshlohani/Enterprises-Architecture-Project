package com.example.userws.domain;

import com.example.userws.enums.RoleType;
import com.example.userws.enums.Status;
import java.time.Instant;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "users")
@Data
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @CreatedDate
  private Instant createdDate;
  @LastModifiedDate
  private Instant modifiedDate;
  @Enumerated(EnumType.STRING)
  private Status status;
  @CreatedBy
  private String userId;
  private String name;
  private String email;
  private String password;
  //roleType for segregating users
  @Enumerated(EnumType.STRING)
  private RoleType roleType;
  //a user can have multiple roles
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable
  private List<Role> roles;
}
