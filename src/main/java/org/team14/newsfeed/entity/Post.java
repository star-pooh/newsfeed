package org.team14.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "longtext")
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)//
    @JoinColumn(name = "user_id")
    private User user;

    public Post(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    public Post() {

    }

    public void setUser(User user) {
        this.user = user;
    }
}
