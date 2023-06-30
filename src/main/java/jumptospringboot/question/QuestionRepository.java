package jumptospringboot.question;

import java.util.List;

// 리포지토리로 만들기 위해 JpaReprository 인터페이스 상속
import jumptospringboot.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

// 페이징 라이브러리
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// JpaRepository<해당 엔티티의 타입, PK 속성 타입>
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);

    // Pageable 객체를 입력으로 받아 Page<Question> 타입 객체를 리턴하는 findAll 메서드를 생성
    // -> Service 수정
    Page<Question> findAll(Pageable pageable);
}
