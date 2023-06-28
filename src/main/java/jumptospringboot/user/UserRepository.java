package jumptospringboot.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    // 인증후에 사용자에게 부여할 권한이 필요, ADMIN, USER 2개의 권한을 갖는 UserRole을 신규로 작성
    Optional<SiteUser> findByusername(String username);
}
