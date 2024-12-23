package study.advanced_proxy.config.v4_postprocess.postprocess;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * <p>PackageLogTraceProxyPostProcessor 는 원본 객체를 프록시 객체로 변환하는 역할을 한다. 이때 프록
 * 시 팩토리를 사용하는데, 프록시 팩토리는 advisor 가 필요하기 때문에 이 부분은 외부에서 주입 받도록 했다.</p>
 *
 * <p>모든 스프링 빈들에 프록시를 적용할 필요는 없다. 여기서는 특정 패키지와 그 하위에 위치한 스프링 빈들만 프록
 * 시를 적용한다. 여기서는 hello.proxy.app 과 관련된 부분에만 적용하면 된다. 다른 패키지의 객체들은 원본
 * 객체를 그대로 반환한다.</p>
 *
 * <p>프록시 적용 대상의 반환 값을 보면 원본 객체 대신에 프록시 객체를 반환한다. 따라서 스프링 컨테이너에 원본 객
 * 체 대신에 프록시 객체가 스프링 빈으로 등록된다. 원본 객체는 스프링 빈으로 등록되지 않는다.</p>
 */
@Slf4j
@RequiredArgsConstructor
public class PackageLogTracePostProcess implements BeanPostProcessor {
    private final Advisor advisor;
    private final String packageBase;
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("param, bean={}, beanName={}",bean,beanName);
        if (bean.getClass().getPackageName().startsWith(packageBase)) {
            ProxyFactory proxyFactory = new ProxyFactory(bean);
            proxyFactory.addAdvisor(advisor);

            Object proxy = proxyFactory.getProxy();
            log.info("create proxy: target={} proxy={}", bean.getClass(), proxy.getClass());
            return proxy;
        }
        return bean;
    }
}
