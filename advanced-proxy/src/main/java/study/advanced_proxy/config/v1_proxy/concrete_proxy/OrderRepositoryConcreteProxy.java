package study.advanced_proxy.config.v1_proxy.concrete_proxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study.advanced_proxy.app.v2.OrderRepositoryV2;
import study.advanced_proxy.trace.TraceStatus;
import study.advanced_proxy.trace.logtrace.LogTrace;

@Slf4j
@RequiredArgsConstructor
public class OrderRepositoryConcreteProxy extends OrderRepositoryV2 {
    private final OrderRepositoryV2 target;
    private final LogTrace trace;

    @Override
    public void save(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderRepository.save()");
            target.save(itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status,e);
            throw e;
        }
    }
}
