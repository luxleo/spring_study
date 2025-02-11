package study.advanced.app.v4;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.advanced.trace.TraceStatus;
import study.advanced.trace.logtrace.LogTrace;
import study.advanced.trace.template.AbstractTemplate;

@RequiredArgsConstructor
@Service
public class OrderServiceV4 {
    private final OrderRepositoryV4 orderRepository;
    private final LogTrace trace;
    public void orderItem(String itemId) {
        AbstractTemplate<Void> template = new AbstractTemplate<Void>(trace) {
            @Override
            protected Void call() {
                orderRepository.save(itemId);
                return null;
            }
        };
        template.execute("OrderService.orderItem()");
        TraceStatus status = null;
    }
}
