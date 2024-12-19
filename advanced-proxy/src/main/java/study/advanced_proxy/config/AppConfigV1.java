package study.advanced_proxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.advanced_proxy.app.v1.*;

@Configuration
public class AppConfigV1 {
    @Bean
    public OrderRepositoryV1 orderRepositoryV1() {
        return new OrderRepositoryImplV1();
    }

    @Bean
    public OrderServiceV1 orderServiceV1() {
        return new OrderServiceImplV1(orderRepositoryV1());
    }

    @Bean
    public OrderControllerV1 orderControllerV1() {
        return new OrderControllerImplV1(orderServiceV1());
    }
}
