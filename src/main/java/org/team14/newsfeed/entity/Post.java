package org.team14.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    // 게시물 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    @Column(nullable = false)
    private String title;

    // 내용
    @Column(columnDefinition = "longtext", nullable = false)
    private String contents;

    // 사용자 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    private Post(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    protected Post() {

    }

    public static Post of(String title, String contents, User user) {
        return new Post(title, contents, user);
    }
}

