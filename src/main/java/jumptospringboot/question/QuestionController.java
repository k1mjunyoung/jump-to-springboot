package jumptospringboot.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
// 변하는 id 값을 얻을 때에는 위와 같이 @PathVariable 애너테이션을 사용
import org.springframework.web.bind.annotation.PathVariable;

// 생성자 자동생성
import lombok.RequiredArgsConstructor;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    // final이 붙은 속성을 포함하는 생성자를 자동으로 생성
    // private final QuestionRepository questionRepository;
    // 레포지토리 대신 서비스를 사용하도록 수정
    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {
        // QuestionController의 list 메서드에서 조회한 질문 목록 데이터를 "questionList"라는 이름으로 Model 객체에 저장
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }
}
