package study.advanced.app.v4;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.advanced.trace.logtrace.LogTrace;
import study.advanced.trace.template.AbstractTemplate;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV4 {
    private static final int DEFAULT_SLEEP_TIME = 1000;
    private final LogTrace trace;
    public void save(String itemId) {
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                if (itemId.equals("ex")) {
                    throw new IllegalStateException("예외 발생!");
                }
                sleep();
                return null;
            }
        };
        template.execute("OrderRepository.save()");
    }
    private void sleep() {
        try {
            Thread.sleep(DEFAULT_SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
