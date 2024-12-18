package study.advanced.trace.template;

import study.advanced.trace.TraceStatus;
import study.advanced.trace.logtrace.LogTrace;

/**
 * <p>템플릿 메서드 패턴은 변하는 부분과 변하지 않는 부분을 분리하여 SRP를 준수할수 있다는
 * 장점이 있다.</p>
 * <p>하지만 템플릿에 메서드가 추가 되면 AbstractTemplate을 상속 받은 모든 클래스를
 * 수정해 주어야 한다는 단점이 있다.</p>
 * @param <T>
 */
public abstract class AbstractTemplate<T> {
    private final LogTrace trace;

    public AbstractTemplate(LogTrace trace) {
        this.trace = trace;
    }

    public T execute(String message) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);
            T result = call();
            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status,e);
            throw e;
        }
    }

    protected abstract T call();
}
