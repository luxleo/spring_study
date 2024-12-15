package study.advanced.trace.hellotrace;

import org.junit.jupiter.api.Test;
import study.advanced.trace.TraceStatus;

import static org.junit.jupiter.api.Assertions.*;

class HelloTraceV1Test {
    @Test
    void begin_end() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus begin = trace.begin("hello");
        trace.end(begin);
    }

    @Test
    void begin_exception() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus begin = trace.begin("exception hello");
        trace.exception(begin, new IllegalArgumentException());
    }
}