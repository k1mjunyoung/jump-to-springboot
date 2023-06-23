package jumptospringboot;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<해당 엔티티의 타입, PK 속성 타입>
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);
}
