package jumptospringboot.question;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import jumptospringboot.DataNotFoundException;

// 페이징 라이브러리
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

// 내림차순 조회 라이브러리
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

import jumptospringboot.user.SiteUser;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    /* 페이징 X
    public List<Question> getList() {
        return this.questionRepository.findAll();
    }
    */

    // 페이징
    // getList 메서드는 정수 타입의 페이지번호를 입력받아 해당 페이지의 질문 목록을 리턴하는 메서드로 변경
    // Pageable 객체를 생성할때 사용한 PageRequest.of(page, 10)에서 page는 조회할 페이지의 번호이고
    // 10은 한 페이지에 보여줄 게시물의 갯수를 의미
    // -> Question 서비스의 getList 메서드의 입출력 구조가 변경되었으므로 Question 컨트롤러도 수정
    public Page<Question> getList(int page) {
        // 내림차순 조회
        // Sort.Order 객체로 구성된 리스트에 Sort.Order 객체를 추가하고 Sort.by(소트리스트)로 소트 객체를 생성
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        // 게시물을 역순으로 조회하기 위해서는 위와 같이 PageRequest.of 메서드의 세번째 파라미터로 Sort 객체를 전달
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
    }

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        // Question 객체는 Optional 객체이기 때문에 위와 같이 isPresent 메서드로 해당 데이터가 존재하는지 검사하는 로직이 필요
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, SiteUser user) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        this.questionRepository.save(q);
    }
}
