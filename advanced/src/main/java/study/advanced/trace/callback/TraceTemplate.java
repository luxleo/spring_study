package study.advanced.trace.callback;

import lombok.extern.slf4j.Slf4j;
import study.advanced.trace.TraceStatus;
import study.advanced.trace.logtrace.LogTrace;

@Slf4j
public class TraceTemplate {
    private final LogTrace trace;

    public TraceTemplate(LogTrace trace) {
        this.trace = trace;
    }

    public <T> T execute(String message, TraceCallBack<T> callBack) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);
            T result = callBack.call();
            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status,e);
            throw e;
        }
    }
}
