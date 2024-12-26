package org.team14.newsfeed.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        // URL 패턴에 기반한 CORS 설정
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        /*
          CORS(Cross-Origin Resource Sharing) : 
          다른 출처(도메인, 포트, 프로토콜 등) 간의 리소스 공유를 허용하거나 제한하는 메커니즘으로서,
          클라이언트 - 서버 간의 통신에서 보안과 유연성을 모두 확보하기 위한 설정
          
          보안 상의 이유로 인해 기본적으로 서로 다른 출처에서의 요청을 차단하지만, 
          CORS 설정을 통해 특정 조건에서 요청 허용(특정 조건의 아래와 같음)
         */
        // 쿠키나 자격 증명을 포함한 요청 허용
        config.setAllowCredentials(true);
        // 모든 출처에서의 요청 허용
        config.addAllowedOriginPattern("*");
        // 모든 헤더 허용
        config.addAllowedHeader("*");
        // 모든 HTTP 메소드 허용
        config.addAllowedMethod("*");
        // 애플리케이션의 모든 URL 경로에 대해 CORS 설정
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
