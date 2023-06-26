package jumptospringboot.question;

import java.util.List;

// 리포지토리로 만들기 위해 JpaReprository 인터페이스 상속
import jumptospringboot.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<해당 엔티티의 타입, PK 속성 타입>
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);
}
