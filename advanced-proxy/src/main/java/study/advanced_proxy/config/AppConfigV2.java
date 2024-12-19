package study.advanced_proxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.advanced_proxy.app.v2.OrderControllerV2;
import study.advanced_proxy.app.v2.OrderRepositoryV2;
import study.advanced_proxy.app.v2.OrderServiceV2;

@Configuration
public class AppConfigV2 {
    @Bean
    public OrderRepositoryV2 orderRepositoryV2() {
        return new OrderRepositoryV2();
    }

    @Bean
    public OrderServiceV2 orderServiceV2() {
        return new OrderServiceV2(orderRepositoryV2());
    }

    @Bean
    public OrderControllerV2 orderControllerV2() {

        return new OrderControllerV2(orderServiceV2());
    }
}
