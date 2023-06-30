package jumptospringboot.question;

import java.util.List;

// 리포지토리로 만들기 위해 JpaReprository 인터페이스 상속
import jumptospringboot.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

// 페이징 라이브러리
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// 검색 라이브러리
import org.springframework.data.jpa.domain.Specification;

// 쿼리 라이브러리
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


// JpaRepository<해당 엔티티의 타입, PK 속성 타입>
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);

    // Pageable 객체를 입력으로 받아 Page<Question> 타입 객체를 리턴하는 findAll 메서드를 생성
    // -> Service 수정
    Page<Question> findAll(Pageable pageable);

    Page<Question> findAll(Specification<Question> spec, Pageable pageable);

    // Specification 대신 쿼리 사용 시
    @Query("select "
            + "distinct q "
            + "from Question q "
            + "left outer join SiteUser u1 on q.author=u1 "
            + "left outer join Answer a on a.question=q "
            + "left outer join SiteUser u2 on a.author=u2 "
            + "where "
            + "   q.subject like %:kw% "
            + "   or q.content like %:kw% "
            + "   or u1.username like %:kw% "
            + "   or a.content like %:kw% "
            + "   or u2.username like %:kw% ")
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
