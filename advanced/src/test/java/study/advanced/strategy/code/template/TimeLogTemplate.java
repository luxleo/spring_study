package study.advanced.strategy.code.template;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>결국 전략 패턴 중 파라미터로 넘어온 전략을 실행하는 컨텍스트가
 * <strong>템플릿 콜백 패턴</strong>이다.</p>
 */
@Slf4j
public class TimeLogTemplate {
    public void execute(CallBack callBack) {
        long startTime = System.currentTimeMillis();
        callBack.call(); //위임
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}
