package org.team14.newsfeed.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.team14.newsfeed.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
}
