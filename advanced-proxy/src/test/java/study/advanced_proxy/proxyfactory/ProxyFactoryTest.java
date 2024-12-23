package study.advanced_proxy.proxyfactory;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import study.advanced_proxy.common.ConcreteService;
import study.advanced_proxy.common.ServiceImpl;
import study.advanced_proxy.common.ServiceInterface;
import study.advanced_proxy.common.advice.TimeAdvice;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyFactoryTest {
    @DisplayName("인터페이스가 있으면 JdkDynamicProxy이용한다.")
    @Test
    void interfaceProxy() {
        //given
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass={}",target.getClass());
        log.info("proxyClass={}",proxy.getClass());
        proxy.save();

        //then
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }

    @DisplayName("인터페이스 없으면 CGLIB이용하여 프록시 생성한다.")
    @Test
    void concreteProxy() {
        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());

        ConcreteService proxy =(ConcreteService) proxyFactory.getProxy();
        proxy.call();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }

    @DisplayName("인터페이스가 있더라도 ProxyFactory.setProxyTargetClass(true)로 설정시 CGLIB 이용한다.")
    @Test
    void proxyTargetClass() {
        //given
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // 인터페이스 존재 유무와 무관하게 CGLIB 사용한다.
        proxyFactory.addAdvice(new TimeAdvice());

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass={}",target.getClass());
        log.info("proxyClass={}",proxy.getClass());
        proxy.save();

        //then
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }
}
