package study.advanced_proxy.config.v1_proxy.interface_proxy;

import lombok.RequiredArgsConstructor;
import study.advanced_proxy.app.v1.OrderRepositoryV1;
import study.advanced_proxy.trace.TraceStatus;
import study.advanced_proxy.trace.logtrace.LogTrace;

@RequiredArgsConstructor
public class OrderRepositoryInterfaceProxy implements OrderRepositoryV1 {
    private final OrderRepositoryV1 target;
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
