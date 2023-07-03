package jumptospringboot.entity;

import java.time.LocalDateTime;
import java.util.List;

// sprintboot 2.X 버전은 jakarta -> javax
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;

// 추천 기능에 필요한 라이브러리
import java.util.Set;
import javax.persistence.ManyToMany;

import jumptospringboot.user.SiteUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    // 여러개의 질문이 한 명의 사용자에게 작성될 수 있으므로 @ManyToOne 관계가 성립
    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;

    @ManyToMany
    Set<SiteUser> voter;
}
