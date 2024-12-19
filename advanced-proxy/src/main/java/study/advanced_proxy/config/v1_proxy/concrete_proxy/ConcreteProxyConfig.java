package study.advanced_proxy.config.v1_proxy.concrete_proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.advanced_proxy.app.v2.OrderControllerV2;
import study.advanced_proxy.app.v2.OrderRepositoryV2;
import study.advanced_proxy.app.v2.OrderServiceV2;
import study.advanced_proxy.trace.logtrace.LogTrace;

@Configuration
public class ConcreteProxyConfig {
    @Bean
    public OrderRepositoryV2 orderRepository(final LogTrace trace) {
        OrderRepositoryV2 target = new OrderRepositoryV2();
        return new OrderRepositoryConcreteProxy(target, trace);
    }

    @Bean
    public OrderServiceV2 orderService(final LogTrace trace) {
        OrderServiceV2 target = new OrderServiceV2(orderRepository(trace));
        return new OrderServiceConcreteProxy(target, trace);
    }

    @Bean
    public OrderControllerV2 orderController(final LogTrace trace) {
        OrderControllerV2 target = new OrderControllerV2(orderService(trace));
        return new OrderControllerConcreteProxy(trace, target);
    }
}
