package study.advanced.strategy.code;

import lombok.extern.slf4j.Slf4j;

/**
 * 전략을 인수롤 전달 받는 방식.
 * 단점 : 필드로 전략을 가지고 있지 않아 다수의 Context를 만들 필요는 없지만,
 * 로직(execute)를 실행할 때마다 새로 인자로 전달해야한다는 문제점이 있다.
 */
@Slf4j
public class ContextV2 {

    public void execute(Strategy strategy) {
        long startTime = System.currentTimeMillis();
//비즈니스 로직 실행
        strategy.call(); //위임
//비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
}
