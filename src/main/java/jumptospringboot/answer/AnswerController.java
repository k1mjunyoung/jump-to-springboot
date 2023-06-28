package jumptospringboot.answer;

import jumptospringboot.question.Question;
import jumptospringboot.question.QuestionService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;

// 현재 로그인한 사용자에 대한 정보를 알기 위해서는 스프링 시큐리티가 제공하는 Principal 객체를 사용
import java.security.Principal;

// 작성자 조회 기능 추가
import jumptospringboot.user.SiteUser;
import jumptospringboot.user.UserService;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    // @PreAuthorize("isAuthenticated()") 애너테이션이 붙은 메서드는 로그인이 필요한 메서드를 의미
    // 만약 @PreAuthorize("isAuthenticated()") 애너테이션이 적용된 메서드가 로그아웃 상태에서 호출되면 로그인 페이지로 이동
    // -> SecurityConfig
    @PreAuthorize("isAuthenticated()")
    // @PostMapping은 @GetMapping과 동일하게 매핑을 담당하는 역할을 하지만 POST요청만 받아들일 경우에 사용
    @PostMapping("create/{id}")
    // createAnswer 메서드에 Principal 객체를 매개변수로 지정
    public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        // principal 객체를 통해 사용자명을 얻은 후에 사용자명을 통해 SiteUser 객체를 얻어서
        // 답변을 등록하는 AnswerService의 create 메서드에 전달하여 답변을 저장
        SiteUser siteUser = this.userService.getUser(principal.getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }

        this.answerService.create(question, answerForm.getContent(), siteUser);

        return String.format("redirect:/question/detail/%s", id);
    }
}
