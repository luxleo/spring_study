package study.advanced.template;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.advanced.template.code.AbstractTemplate;
import study.advanced.template.code.SubClassLogic1;
import study.advanced.template.code.SubClassLogic2;

@Slf4j
public class TemplateMethodTest {
    @Test
    void templateMethodV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        log.info("비즈니스 로직1 실행");
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        log.info("비즈니스 로직2 실행");
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    @Test
    void templateMethodV1() {
        AbstractTemplate logic1 = new SubClassLogic1();
        AbstractTemplate logic2 = new SubClassLogic2();
        logic1.execute();
        logic2.execute();
    }

    @Test
    @DisplayName("익명 내부 클래스로 템플릿 패턴을 더 간단히 구현할 수 있다.")
    void templateMethodV2() {
        AbstractTemplate logic1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("logic1");
            }
        };
        AbstractTemplate logic2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("logic2");
            }
        };
        logic1();
        logic2();
    }
}
