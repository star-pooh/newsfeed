package org.team14.newsfeed.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, columnDefinition = "TINYINT(1)")
  private boolean isValidUser;

  public User(Long id, String name, String email, String password, boolean isValidUser) {

    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.isValidUser = isValidUser;
  }

  public User() {
  }
}
