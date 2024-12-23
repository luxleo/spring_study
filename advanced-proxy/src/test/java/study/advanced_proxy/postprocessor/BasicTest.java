package study.advanced_proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
public class BasicTest {

    @Test
    void AIsBeanButBIsNot() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicConfig.class);
        A beanA =(A) applicationContext.getBean("beanA");
        beanA.helloA();

        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean("beanB"));
    }
    @Slf4j
    @Configuration
    static class BasicConfig {
        @Bean
        A beanA() {
            return new A();
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
