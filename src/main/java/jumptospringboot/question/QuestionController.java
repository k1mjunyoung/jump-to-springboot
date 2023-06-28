package jumptospringboot.question;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
// 변하는 id 값을 얻을 때에는 위와 같이 @PathVariable 애너테이션을 사용
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// 생성자 자동생성
import lombok.RequiredArgsConstructor;

// 입력값 검증
import javax.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.List;

import jumptospringboot.answer.AnswerForm;
import jumptospringboot.user.SiteUser;
import jumptospringboot.user.UserService;

// 페이징 라이브러리
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    // final이 붙은 속성을 포함하는 생성자를 자동으로 생성
    // private final QuestionRepository questionRepository;
    // 레포지토리 대신 서비스를 사용하도록 수정
    private final QuestionService questionService;
    private final UserService userService;

    /* 페이징 X
    @GetMapping("/list")
    public String list(Model model) {
        // QuestionController의 list 메서드에서 조회한 질문 목록 데이터를 "questionList"라는 이름으로 Model 객체에 저장
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }
    */

    // 페이징
    // http://localhost:8080/question/list?page=0 처럼 GET 방식으로 요청된 URL에서 page값을 가져오기 위해
    // @RequestParam(value="page", defaultValue="0") int page 매개변수가 list 메서드에 추가
    // 템플릿에 Page<Question> 객체인 paging을 전달
    // -> 기존에 전달했던 이름인 "questionList" 대신 "paging" 이름으로 템플릿에 전달했기 때문에 템플릿도 변경
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page) {
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize(("isAuthenticated()"))
    @PostMapping("/create")
    // questionCreate 메서드의 매개변수를 subject, content 대신 QuestionForm 객체로 변경
    // @Valid 애너테이션을 적용하면 QuestionForm의 @NotEmpty, @Size 등으로 설정한 검증 기능이 동작
    // BindingResult 매개변수는 @Valid 애너테이션으로 인해 검증이 수행된 결과를 의미하는 객체
    // BindingResult 매개변수는 항상 @Valid 매개변수 바로 뒤에 위치해야 한다.
    // 만약 2개의 매개변수의 위치가 정확하지 않다면 @Valid만 적용이 되어 입력값 검증 실패 시 400 오류가 발생한다.
    // 입력값도 입력하지 않았기 때문에 QuestionForm의 @NotEmpty에 의해 Validation이 실패하여 다시 질문 등록 화면에 머물러 있을 것이다.

    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        SiteUser siteUser = this.userService.getUser(principal.getName());

        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/question/list";
    }
}
