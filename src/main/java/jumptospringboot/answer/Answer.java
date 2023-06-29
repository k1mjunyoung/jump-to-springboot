package jumptospringboot.answer;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import jumptospringboot.question.Question;
import lombok.Getter;
import lombok.Setter;

import jumptospringboot.user.SiteUser;

// 추천 기능에 필요한 라이브러리
import java.util.Set;
import javax.persistence.ManyToMany;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Question question;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;

    // @ManyToMany 관계로 속성을 생성하면 새로운 테이블을 생성하여 데이터를 관리
    @ManyToMany
    Set<SiteUser> voter;
}
