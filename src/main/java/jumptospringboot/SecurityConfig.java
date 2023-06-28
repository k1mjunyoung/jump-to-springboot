package jumptospringboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

// AuthenticationManager 빈 생성
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// 로그아웃 상태에서 글 작성 로그인 페이지로 이동시켜주는 라이브러리
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableWebSecurity
// SecurityConfig에 적용한@EnableMethodSecurity 애너테이션의 prePostEnabled = true 설정은
// QuestionController와 AnswerController에서 로그인 여부를 판별하기 위해 사용했던
// @PreAuthorize 애너테이션을 사용하기 위해 반드시 필요
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().requestMatchers(
                    new AntPathRequestMatcher("/**")).permitAll()
                .and()
                    // /h2-console/로 시작하는 URL은 CSRF 검증을 하지 않는다는 설정
                    .csrf()
                    .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))

                .and()
                    .headers()
                    .addHeaderWriter(new XFrameOptionsHeaderWriter(
                            XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                // 로그인 기능 추가 -> UserController
                .and()
                    .formLogin()
                    .loginPage("/user/login")
                    .defaultSuccessUrl("/")
                // 로그아웃 기능 추가
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                ;
        return http.build();
    }

    // 암호화 방식 변경하려면 이 Bean만 수정
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager는 스프링 시큐리티의 인증을 담당
    // AuthenticationManager는 사용자 인증시 앞에서 작성한 UserSecurityService와 PasswordEncoder를 사용
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
