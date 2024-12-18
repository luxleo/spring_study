package study.advanced.template.code;

import lombok.extern.slf4j.Slf4j;

/**
 * 구현해야할 로직을 정의 하므로 상속받은 하위 클래스에 영향을 미친다.
 * 즉 OCP를 위반한다.
 */
@Slf4j
public abstract class AbstractTemplate {
    public void execute() {
        long startTime = System.currentTimeMillis();
        call();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    protected abstract void call();
}
