package org.team14.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

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
}
