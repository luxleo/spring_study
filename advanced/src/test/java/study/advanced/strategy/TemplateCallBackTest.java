package study.advanced.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.advanced.strategy.code.template.CallBack;
import study.advanced.strategy.code.template.TimeLogTemplate;

@Slf4j
public class TemplateCallBackTest {
    @DisplayName("템플릿 콜백 패턴 : 내부 익명함수")
    @Test
    void templateCallBackV1() {
        TimeLogTemplate template = new TimeLogTemplate();
        template.execute(new CallBack() {
            @Override
            public void call() {
                log.info("execute bussiness info");
            }
        });
    }

    @DisplayName("템플릿 콜백 패턴 : 람다")
    @Test
    void templateCallBackV2() {
        TimeLogTemplate template = new TimeLogTemplate();
        template.execute(() -> log.info("execute bussiness info"));
    }
}
