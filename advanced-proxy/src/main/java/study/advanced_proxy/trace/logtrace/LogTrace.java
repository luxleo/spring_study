package study.advanced_proxy.trace.logtrace;


import study.advanced_proxy.trace.TraceStatus;

public interface LogTrace {
    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);
}
