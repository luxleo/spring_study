package study.advanced_proxy.config.v1_proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.advanced_proxy.app.v1.*;
import study.advanced_proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import study.advanced_proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import study.advanced_proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import study.advanced_proxy.trace.logtrace.LogTrace;

@Configuration
public class InterfaceProxyConfig {
    @Bean
    public OrderControllerV1 orderController(final LogTrace trace) {
        OrderControllerV1 target = new OrderControllerImplV1(orderService(trace));
        return new OrderControllerInterfaceProxy(trace,target );
    }

    @Bean
    public OrderServiceV1 orderService(final LogTrace trace) {
        OrderServiceV1 target = new OrderServiceImplV1(orderRepository(trace));
        return new OrderServiceInterfaceProxy(target,trace);
    }

    @Bean
    public OrderRepositoryV1 orderRepository(final LogTrace trace) {
        OrderRepositoryV1 target = new OrderRepositoryImplV1();
        return new OrderRepositoryInterfaceProxy(target, trace);
    }
}
