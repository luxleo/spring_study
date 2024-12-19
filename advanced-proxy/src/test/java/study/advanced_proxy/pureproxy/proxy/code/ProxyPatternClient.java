package study.advanced_proxy.pureproxy.proxy.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ProxyPatternClient {
    private final Subject subject;

    public void execute() {
        subject.operation();
    }
}
