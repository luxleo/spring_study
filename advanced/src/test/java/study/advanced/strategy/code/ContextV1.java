package study.advanced.strategy.code;

import lombok.extern.slf4j.Slf4j;

/**
 * 선조립, 후실행 방식 (전략을 필드로 가진다)
 * 단점 : 변화에 유연하지 못하다 (물론 setter를 사용하여 변경하면 해결 가능하지만,실제 어플리케이션의 싱글톤
 * 기반은 동시성 문제를 일으킴)
 */
@Slf4j
public class ContextV1 {
    private final Strategy strategy;

    public ContextV1(Strategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        long startTime = System.currentTimeMillis();
//비즈니스 로직 실행
        strategy.call(); //위임
//비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}
