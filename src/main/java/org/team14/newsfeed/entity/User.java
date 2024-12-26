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

    /**
     * 새로운 사용자 객체 생성
     *
     * @param username 사용자 이름
     * @param email    사용자 이메일
     * @param password 사용자 비밀번호 (암호화된 상태)
     * @return 생성된 사용자 객체
     */
    public static User of(String username, String email, String password) {
        return new User(username, email, password);
    }

    /**
     * 사용자 이름 수정
     * 유효성 검사 포함
     *
     * @param username 변경할 사용자 이름
     */
    public void updateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("사용자 이름은 필수 입력 값입니다.");
        }
        this.username = username;
    }

    /**
     * 사용자 이메일 수정
     * 유효성 검사 포함
     *
     * @param email 변경할 사용자 이메일
     */
    public void updateEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("이메일 형식이 잘못되었습니다.");
        }
        this.email = email;
    }

    /**
     * 사용자 비밀번호 수정
     * 현재 비밀번호의 일치 여부를 확인하고 새 비밀번호를 암호화하여 저장
     *
     * @param currentPassword 현재 비밀번호
     * @param newPassword     새로운 비밀번호
     * @param passwordEncoder 비밀번호 암호화 도구
     */
    public void changePassword(String currentPassword, String newPassword, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(currentPassword, this.password)) {
            throw new IllegalArgumentException("현재 비밀번호가 올바르지 않습니다.");
        }
        this.password = passwordEncoder.encode(newPassword);
    }
}

