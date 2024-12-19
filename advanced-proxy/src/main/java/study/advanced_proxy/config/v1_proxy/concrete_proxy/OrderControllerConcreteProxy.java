package study.advanced_proxy.config.v1_proxy.concrete_proxy;

import lombok.extern.slf4j.Slf4j;
import study.advanced_proxy.app.v2.OrderControllerV2;
import study.advanced_proxy.trace.TraceStatus;
import study.advanced_proxy.trace.logtrace.LogTrace;

@Slf4j
public class OrderControllerConcreteProxy extends OrderControllerV2 {
    private final LogTrace trace;
    private final OrderControllerV2 target;

    public OrderControllerConcreteProxy(LogTrace trace, OrderControllerV2 target) {
        super(null);
        this.trace = trace;
        this.target = target;
    }

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
        return super.noLog();
    }
}
