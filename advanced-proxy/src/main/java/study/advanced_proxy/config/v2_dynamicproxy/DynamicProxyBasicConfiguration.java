package study.advanced_proxy.config.v2_dynamicproxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.advanced_proxy.app.v1.*;
import study.advanced_proxy.config.v2_dynamicproxy.handler.LogTraceBasicHandler;
import study.advanced_proxy.trace.logtrace.LogTrace;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyBasicConfiguration {
    @Bean
    public OrderRepositoryV1 orderRepository(final LogTrace trace) {
        OrderRepositoryV1 target = new OrderRepositoryImplV1();
        LogTraceBasicHandler handler = new LogTraceBasicHandler(trace, target);
        return (OrderRepositoryV1) Proxy.newProxyInstance(
                OrderRepositoryV1.class.getClassLoader(),
                new Class[]{OrderRepositoryV1.class},
                handler
        );
    }

    @Bean
    public OrderServiceV1 orderService(final LogTrace trace) {
        OrderServiceV1 target = new OrderServiceImplV1(orderRepository(trace));
        LogTraceBasicHandler handler = new LogTraceBasicHandler(trace, target);
        return (OrderServiceV1) Proxy.newProxyInstance(
                OrderServiceV1.class.getClassLoader(),
                new Class[]{OrderServiceV1.class},
                handler
        );
    }

    @Bean
    public OrderControllerV1 orderController(final LogTrace trace) {
        OrderControllerV1 target = new OrderControllerImplV1(orderService(trace));
        LogTraceBasicHandler handler = new LogTraceBasicHandler(trace, target);
        return (OrderControllerV1) Proxy.newProxyInstance(
                OrderControllerV1.class.getClassLoader(),
                new Class[]{OrderControllerV1.class},
                handler
        );
    }
}
