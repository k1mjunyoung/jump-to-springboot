package jumptospringboot.service;

import jumptospringboot.DataNotFoundException;
import jumptospringboot.entity.Answer;
import jumptospringboot.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import jumptospringboot.entity.Question;

import java.time.LocalDateTime;
import java.util.Optional;

import jumptospringboot.user.SiteUser;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public Answer create(Question question, String content, SiteUser author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        // create 메서드에 SiteUser 객체를 추가로 전달받아 답변 저장시 author 속성에 세팅
        answer.setAuthor(author);
        this.answerRepository.save(answer);
        return answer;
    }

    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }

    public void vote(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);
        this.answerRepository.save(answer);
    }
}
