package study.advanced_proxy.pureproxy.proxy.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CacheProxy implements Subject{
    private final Subject target;
    private String cachedValue;

    @Override
    public String operation() {
        log.info("call proxy");
        if (cachedValue == null) {
            cachedValue = target.operation();
        }
        return cachedValue;
    }
}
