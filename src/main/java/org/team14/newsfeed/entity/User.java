package org.team14.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;
import org.team14.newsfeed.config.PasswordEncoder;

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

    // 팔로우
    @OneToMany(mappedBy = "following", fetch = FetchType.LAZY)
    private List<FollowUser> following;


    @OneToMany(mappedBy = "followed", fetch = FetchType.LAZY)
    private List<FollowUser> followed;


    private User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    protected User() {
    }

    public static User of(String username, String email, String password) {
        return new User(username, email, password);
    }


    // Setter
    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("사용자 이름은 필수 입력 값입니다.");
        }
        this.username = username;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("이메일 형식이 잘못되었습니다.");
        }
        this.email = email;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수 입력 값입니다.");
        }
        this.password = password;
    }

    // 사용자 비밀번호 변경 및 검증
    public void changePassword(String currentPassword, String newPassword, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(currentPassword, this.password)) {
            throw new IllegalArgumentException("현재 비밀번호가 올바르지 않습니다.");
        }
        if (passwordEncoder.matches(newPassword, this.password)) {
            throw new IllegalArgumentException("새 비밀번호는 기존 비밀번호와 달라야 합니다.");
        }
        if (!newPassword.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$")) {
            throw new IllegalArgumentException("비밀번호는 최소 8자리 이상, 영문/숫자/특수문자를 포함해야 합니다.");
        }
        this.password = passwordEncoder.encode(newPassword);
    }
}

