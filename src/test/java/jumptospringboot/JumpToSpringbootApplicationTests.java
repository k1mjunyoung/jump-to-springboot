package jumptospringboot;

import jumptospringboot.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
class JumpToSpringbootApplicationTests {

	@Autowired
	private QuestionService questionService;

	@Test
	void testJPA() {
		for (int i=1; i<=300; i++) {
			String subject = String.format("테스트 데이터 입니다 [%03d]", i);
			String content = String.format("내용무 [%03d]", i);
			this.questionService.create(subject, content, null);
		}
	}
}
