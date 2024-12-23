package study.advanced_proxy.config.v3_proxyfactory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.advanced_proxy.app.v1.*;
import study.advanced_proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import study.advanced_proxy.trace.logtrace.LogTrace;

@Slf4j
@Configuration
public class ProxyFactoryConfigV1 {

    @Bean
    public OrderControllerV1 orderController(final LogTrace trace) {
        OrderControllerImplV1 target = new OrderControllerImplV1(orderService(trace));
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvisor(getAdvisor(trace));

        OrderControllerV1 proxy = (OrderControllerV1) proxyFactory.getProxy();
        log.info("ProxyFactory, proxy={}, target={}",proxy.getClass(),target.getClass());
        return proxy;
    }
    @Bean
    public OrderServiceV1 orderService(final LogTrace trace) {
        OrderServiceImplV1 target = new OrderServiceImplV1(orderRepository(trace));
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvisor(getAdvisor(trace));

        OrderServiceV1 proxy = (OrderServiceV1) proxyFactory.getProxy();
        log.info("ProxyFactory, proxy={}, target={}",proxy.getClass(),target.getClass());
        return proxy;
    }
    @Bean
    public OrderRepositoryV1 orderRepository(final LogTrace trace) {
        OrderRepositoryV1 target = new OrderRepositoryImplV1();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvisor(getAdvisor(trace));

        OrderRepositoryV1 proxy = (OrderRepositoryV1) proxyFactory.getProxy();
        log.info("ProxyFactory, proxy={}, target={}",proxy.getClass(),target.getClass());
        return proxy;
    }
    private Advisor getAdvisor(final LogTrace trace) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("save*","request*","order*");

        return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(trace));
    }
}
