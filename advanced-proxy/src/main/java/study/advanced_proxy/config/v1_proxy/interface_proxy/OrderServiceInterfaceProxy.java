package study.advanced_proxy.config.v1_proxy.interface_proxy;

import lombok.RequiredArgsConstructor;
import study.advanced_proxy.app.v1.OrderServiceV1;
import study.advanced_proxy.trace.TraceStatus;
import study.advanced_proxy.trace.logtrace.LogTrace;

@RequiredArgsConstructor
public class OrderServiceInterfaceProxy implements OrderServiceV1{
    private final OrderServiceV1 target;
    private final LogTrace trace;

    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderService.orderItem()");
            target.orderItem(itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status,e);
            throw e;
        }
    }
}
