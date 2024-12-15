package study.advanced;

import com.sun.jdi.Field;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.advanced.trace.logtrace.FieldLogTrace;
import study.advanced.trace.logtrace.LogTrace;

@Configuration
public class LogTraceConfig {
    @Bean
    public LogTrace logTrace() {
        return new FieldLogTrace();
    }
}
