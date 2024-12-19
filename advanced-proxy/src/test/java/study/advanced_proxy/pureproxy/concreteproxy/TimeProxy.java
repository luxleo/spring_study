package study.advanced_proxy.pureproxy.concreteproxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TimeProxy extends ConcreteLogic{
    private final ConcreteLogic target;
    @Override
    public String operation() {
        log.info("TimeProxy.operation()");
        long startTime = System.currentTimeMillis();
        String result = target.operation();
        long endTime = System.currentTimeMillis();
        log.info("TimeProxy end, resultTime={}",endTime - startTime);
        return result;
    }
}
