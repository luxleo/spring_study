package study.advanced.app.v5;

import org.springframework.stereotype.Repository;
import study.advanced.trace.callback.TraceTemplate;
import study.advanced.trace.logtrace.LogTrace;

@Repository
public class OrderRepositoryV5 {
    private static final int DEFAULT_SLEEP_TIME = 1000;

    private final TraceTemplate template;

    public OrderRepositoryV5(LogTrace trace) {
        this.template = new TraceTemplate(trace);
    }

    public void save(String itemId) {
        template.execute("OrderRepository.save()", () -> {
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!");
            }
            sleep();
            return null;
        });
    }
    private void sleep() {
        try {
            Thread.sleep(DEFAULT_SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
