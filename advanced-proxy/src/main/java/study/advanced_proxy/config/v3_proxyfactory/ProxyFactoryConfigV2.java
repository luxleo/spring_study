package study.advanced_proxy.config.v3_proxyfactory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.advanced_proxy.app.v2.OrderControllerV2;
import study.advanced_proxy.app.v2.OrderRepositoryV2;
import study.advanced_proxy.app.v2.OrderServiceV2;
import study.advanced_proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import study.advanced_proxy.trace.logtrace.LogTrace;

@Slf4j
@Configuration
public class ProxyFactoryConfigV2 {

    @Bean
    public OrderControllerV2 orderController(final LogTrace trace) {
        OrderControllerV2 target = new OrderControllerV2(orderService(trace));
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvisor(getAdvisor(trace));

        OrderControllerV2 proxy = (OrderControllerV2) proxyFactory.getProxy();
        log.info("ProxyFactory, proxy={}, target={}",proxy.getClass(),target.getClass());
        return proxy;
    }
    @Bean
    public OrderServiceV2 orderService(final LogTrace trace) {
        OrderServiceV2 target = new OrderServiceV2(orderRepository(trace));
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvisor(getAdvisor(trace));

        OrderServiceV2 proxy = (OrderServiceV2) proxyFactory.getProxy();
        log.info("ProxyFactory, proxy={}, target={}",proxy.getClass(),target.getClass());
        return proxy;
    }
    @Bean
    public OrderRepositoryV2 orderRepository(final LogTrace trace) {
        OrderRepositoryV2 target = new OrderRepositoryV2();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvisor(getAdvisor(trace));

        OrderRepositoryV2 proxy = (OrderRepositoryV2) proxyFactory.getProxy();
        log.info("ProxyFactory, proxy={}, target={}",proxy.getClass(),target.getClass());
        return proxy;
    }
    private Advisor getAdvisor(final LogTrace trace) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("save*","request*","order*");

        return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(trace));
    }
}
