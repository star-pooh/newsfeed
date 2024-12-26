package org.team14.newsfeed.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.team14.newsfeed.repository.UserRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    /*
      loadUserByUsername을 오버라이딩 해야 하는 이유는?
      UserDetailsService 인터페이스가 loadUserByUsername 메소드를 필수로 정의하고 있기 때문

      Spring Security에서는 사용자의 인증 정보를 조회하기 위해 UserDetailsService를 사용하며,
      loadUserByUsername 메소드를 구현하여 사용자 정보를 데이터베이스나 다른 저장소에서 가져옴

      따라서, loadUserByUsername 메소드를 오버라이드하여 아이디, 이메일과 같은 사용자 식별자를 통해
      사용자 정보를 조회하고, UserDetails 객체로 반환하는 로직을 구현해야 인증 및 인가 과정에서 사용자 정보를 제공할 수 있음
     */

    /**
     * 이메일을 통해 사용자 정보를 조회하고, UserDetails 객체로 변환
     *
     * @param email 이메일
     * @return UserDetails 객체
     * @throws UsernameNotFoundException 일치하는 사용자가 없을 경우의 에러
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email)
                .map(user -> new User(
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // 권한 설정 (ROLE_USER)
                )).orElseThrow(() -> new UsernameNotFoundException("일치하는 사용자를 찾을 수 없습니다."));
    }
}
