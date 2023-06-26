package jumptospringboot.answer;

import jumptospringboot.question.Question;
import jumptospringboot.question.QuestionService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    // @PostMapping은 @GetMapping과 동일하게 매핑을 담당하는 역할을 하지만 POST요청만 받아들일 경우에 사용
    @PostMapping("create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam String content) {
        Question question = this.questionService.getQuestion(id);
        // TODO: 답변 저장
        this.answerService.create(question, content);
        return String.format("redirect:/question/detail/%s", id);
    }
}
