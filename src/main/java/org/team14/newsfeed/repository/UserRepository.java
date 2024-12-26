package org.team14.newsfeed.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.team14.newsfeed.entity.User;
import org.team14.newsfeed.exception.CustomException;

public interface UserRepository extends JpaRepository<User, Long> {

    default User findUserByUsernameOrElseThrow(String username) {
        return findUserByUsername(username).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND,
                        "Does not exist username" + username));
    }

    Optional<User> findByEmail(String email);

    default User findUserByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(

                () -> new CustomException(HttpStatus.NOT_FOUND,
                        "Dose not exist email" + email));
    }

    Optional<User> findUserByUsername(String username);


    /**
     * 사용자 조회
     * <p>
     * 조건(사용자 이름, 이메일) 유무에 따라 조건식 변경
     *
     * @param username 사용자 이름
     * @param email    이메일
     * @return 조회된 사용자 정보
     */
    @Query("SELECT u FROM User u " +
            "WHERE (:username IS NULL OR u.username = :username) " +
            "AND (:email IS NULL OR u.email = :email)")
    List<User> findByUsernameAndEmail(String username, String email);
}
