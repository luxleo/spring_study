package study.advanced_proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 빈 후처리기를 빈으로 등록하면 자동으로 실행된다.
 *
 * 빈 후처리기는 빈을 조작하고 변경할 수 있는 후킹 포인트이다.
 * 이것은 빈 객체를 조작하거나 심지어 다른 객체로 바꾸어 버릴 수 있을 정도로 막강하다.
 * 여기서 조작이라는 것은 해당 객체의 특정 메서드를 호출하는 것을 뜻한다.
 * 일반적으로 스프링 컨테이너가 등록하는, 특히 컴포넌트 스캔의 대상이 되는 빈들은 중간에 조작할 방법이 없는데, 빈
 * 후처리기를 사용하면 개발자가 등록하는 모든 빈을 중간에 조작할 수 있다. 이 말은 빈 객체를 프록시로 교체하는 것도
 * 가능하다는 뜻이다.
 */
@Slf4j
public class BeanPostProcessTest {

    @Test
    void postProcessTest() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanPostProcessConfig.class);
        B beanA = applicationContext.getBean("beanA", B.class);
        beanA.helloB();

        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(A.class));
    }
    @Slf4j
    @Configuration
    static class BeanPostProcessConfig {
        @Bean
        A beanA() {
            return new A();
        }

        /**
         * 빈 후처리기를 빈으로 등록하면 자동으로 실행된다.
         * @return AToBPostProcess
         */
        @Bean
        AToBPostProcess postProcess() {
            return new AToBPostProcess();
        }
    }

    static class AToBPostProcess implements BeanPostProcessor {
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("bean={}, beanName={}",bean.getClass(), beanName);
            if (bean instanceof A) {
                return new B();
            }
            return bean;
        }
    }

    @Slf4j
    static class A{
        public void helloA() {
            log.info("Hello A");
        }
    }

    @Slf4j
    static class B{
        public void helloB() {
            log.info("hello B");
        }
    }
}
