package org.team14.newsfeed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.team14.newsfeed.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일로 사용자 찾기
    Optional<User> findByEmail(String email);

    // 사용자 이름으로 사용자 찾기
    Optional<User> findByUsername(String username);

    // 이메일로 사용자 조회 및 예외 처리
    default User findByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 이메일로 사용자를 찾을 수 없습니다.: " + email));
    }

    // 사용자 이름으로 사용자 조회 및 예외 처리
    default User findByUsernameOrElseThrow(String username) {
        return findByUsername(username).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자 이름이 없는 사용자를 찾을 수 없습니다 : " + username));
    }

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
