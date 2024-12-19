package study.advanced_proxy.pureproxy.decorator.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TimeDecorator implements Component{
    private final Component component;

    @Override
    public String operation() {
        log.info("TimeDecorator start");
        long start = System.currentTimeMillis();
        String result = component.operation();
        long end = System.currentTimeMillis();
        long resultTime = end - start;

        log.info("TimeDecorator end, result time={}ms",resultTime);
        return result;
    }
}
