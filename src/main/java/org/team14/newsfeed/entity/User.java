package org.team14.newsfeed.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {

  // 사용자 ID
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 사용자 이름
  @Column(nullable = false)
  private String username;

  // 이메일
  @Column(nullable = false)
  private String email;

  // 비밀번호
  @Column(nullable = false)
  private String password;

  // 탈퇴 여부
  @Column(nullable = false, columnDefinition = "TINYINT(1)")
  @ColumnDefault("0")
  private boolean isDeleted;

  private User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public User() {
  }

  public static User of(String username, String email, String password) {
    return new User(username, email, password);
  }
}
