package jumptospringboot.user;

// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jumptospringboot.user.SiteUser;
import jumptospringboot.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import jumptospringboot.DataNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    // User 리포지터리를 사용
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // User 데이터를 생성하는 create 메서드를 추가
    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        // 사용자의 비밀번호는 보안을 위해 반드시 암호화하여 저장
        // 암호화를 위해 시큐리티의 BCryptPasswordEncoder 클래스를 사용
        // BCryptPasswordEncoder는 BCrypt 해싱 함수(BCrypt hashing function)를 사용해서 비밀번호를 암호화
        // BCryptPasswordEncoder 객체를 직접 new로 생성하는 방식보다는 PasswordEncoder 빈(bean)으로 등록해서 사용
        // 암호화 방식을 변경하면 BCryptPasswordEncoder를 사용한 모든 프로그램을 일일이 찾아서 수정해야 하기 때문
        // PasswordEncoder 빈(bean)을 만드는 가장 쉬운 방법은 @Configuration이 적용된 SecurityConfig에 @Bean 메서드를 생성
        // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }
}
