package jumptospringboot.answer;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import jumptospringboot.question.Question;

import java.time.LocalDateTime;

import jumptospringboot.user.SiteUser;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void create(Question question, String content, SiteUser author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        // create 메서드에 SiteUser 객체를 추가로 전달받아 답변 저장시 author 속성에 세팅
        answer.setAuthor(author);
        this.answerRepository.save(answer);
    }
}
