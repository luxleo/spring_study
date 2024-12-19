package study.advanced_proxy.pureproxy.proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import study.advanced_proxy.pureproxy.proxy.code.CacheProxy;
import study.advanced_proxy.pureproxy.proxy.code.ProxyPatternClient;
import study.advanced_proxy.pureproxy.proxy.code.RealSubject;
import study.advanced_proxy.pureproxy.proxy.code.Subject;

public class ProxyPatternTest {
    @DisplayName("캐시를 사용하지 않아서 실행하는 횟수 만큼 시간이 걸린다.")
    @Test
    void noProxyTest() {
        Subject subject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(subject);

        client.execute();
        client.execute();
        client.execute();
    }

    @DisplayName("캐시를 사용하여 시간이 줄어든다.")
    @Test
    void cacheProxyTest() {
        Subject subject = new RealSubject();
        Subject cacheProxy = new CacheProxy(subject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);

        client.execute();
        client.execute();
        client.execute();
    }
}
