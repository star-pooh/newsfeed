package org.team14.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table
public class FollowUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "following_user_id")
    private User followerUserId;

    @ManyToOne
    @JoinColumn(name = "followed_user_id")
    private User followedUserId;

    public FollowUser() {
    }
}
