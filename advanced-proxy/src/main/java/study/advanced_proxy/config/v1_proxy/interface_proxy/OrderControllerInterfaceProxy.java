package study.advanced_proxy.config.v1_proxy.interface_proxy;

import lombok.RequiredArgsConstructor;
import study.advanced_proxy.app.v1.OrderControllerV1;
import study.advanced_proxy.trace.TraceStatus;
import study.advanced_proxy.trace.logtrace.LogTrace;

@RequiredArgsConstructor
public class OrderControllerInterfaceProxy implements OrderControllerV1 {
    private final LogTrace trace;
    private final OrderControllerV1 target;
    @Override
    public String request(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderController.request()");
            String result = target.request(itemId);
            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status,e);
            throw e;
        }
    }

    @Override
    public String noLog() {
        return target.noLog();
    }
}
