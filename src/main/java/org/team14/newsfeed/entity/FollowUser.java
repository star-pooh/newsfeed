package org.team14.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "follow_user")
public class FollowUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "following_user_id", nullable = false)
    private User following;

    @ManyToOne
    @JoinColumn(name = "followed_user_id", nullable = false)
    private User followed;

    public FollowUser(User followed, User following) {
        this.followed = followed;
        this.following = following;
    }

    protected FollowUser() {
    }

    public static FollowUser createFollowRelationship(User following, User followed) {
        return new FollowUser(following, followed);
    }


}
