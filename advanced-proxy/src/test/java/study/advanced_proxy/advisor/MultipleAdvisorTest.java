package study.advanced_proxy.advisor;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import study.advanced_proxy.common.ServiceImpl;
import study.advanced_proxy.common.ServiceInterface;

public class MultipleAdvisorTest {

    @DisplayName("여러 프록시")
    @Test
    void multipleProxy() {
        ServiceImpl target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(Pointcut.TRUE,new Advice1()));
        proxyFactory.setProxyTargetClass(true);
        ServiceImpl proxy1 = (ServiceImpl) proxyFactory.getProxy();

        ProxyFactory proxyFactory2 = new ProxyFactory(proxy1);
        proxyFactory2.addAdvisor(new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2()));
        proxyFactory2.setProxyTargetClass(true);
        ServiceImpl proxy = (ServiceImpl) proxyFactory2.getProxy();

        proxy.save();
    }

    @DisplayName("하나의 프록시, 여러 advisor, 등록한 순서대로 호출된다.")
    @Test
    void multipleAdvisor() {
        ServiceImpl target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2()));
        proxyFactory.addAdvisor(new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1()));

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        proxy.save();

        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
    }

    @Slf4j
    static class Advice1 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("advice1 호출");
            return invocation.proceed();
        }
    }

    @Slf4j
    static class Advice2 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("advice2 호출");
            return invocation.proceed();
        }
    }
}
