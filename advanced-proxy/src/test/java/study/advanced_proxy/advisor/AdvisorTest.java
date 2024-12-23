package study.advanced_proxy.advisor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import study.advanced_proxy.common.ServiceImpl;
import study.advanced_proxy.common.ServiceInterface;
import study.advanced_proxy.common.advice.TimeAdvice;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class AdvisorTest {
    @Test
    void advisorTest1() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());

        proxyFactory.addAdvisor(advisor);
        proxyFactory.setProxyTargetClass(true);

        ServiceImpl proxy = (ServiceImpl) proxyFactory.getProxy();
        proxy.save();

        log.info("targetClass={}",target.getClass());
        log.info("proxyClass={}",proxy.getClass());

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

    @Test
    void customPointcut() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointCut(), new TimeAdvice());
        proxyFactory.addAdvisor(advisor);

        ServiceImpl proxy = (ServiceImpl) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    @DisplayName("스프링이 제공하는 포인트 컷")
    @Test
    void springPointcut() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("save");
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);

        ServiceImpl proxy = (ServiceImpl) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    static class MyPointCut implements Pointcut{

        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }

        static class MyMethodMatcher implements MethodMatcher {
            private static final String MATCH_NAME = "save";
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                boolean result = method.getName().equals(MATCH_NAME);
                log.info("포인트컷 호출 methodName={}, targetClass={}",method.getName(), targetClass);
                log.info("포인트컷 호출 결과: result={}",result);
                return result;
            }

            @Override
            public boolean isRuntime() {
                return false;
            }

            @Override
            public boolean matches(Method method, Class<?> targetClass, Object... args) {
                throw new UnsupportedOperationException();
            }
        }
    }
}
