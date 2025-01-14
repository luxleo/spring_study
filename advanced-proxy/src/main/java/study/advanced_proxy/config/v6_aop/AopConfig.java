package study.advanced_proxy.config.v6_aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import study.advanced_proxy.config.AppConfigV1;
import study.advanced_proxy.config.AppConfigV2;
import study.advanced_proxy.config.v6_aop.aspect.LogTraceAspect;
import study.advanced_proxy.trace.logtrace.LogTrace;

@Import({AppConfigV1.class, AppConfigV2.class})
@Configuration
public class AopConfig {
    // TODO: @Import 된 Config 클래스가 서로 맞으면 자동으로 찾아서 주입한다. LogTrace
    @Bean
    public LogTraceAspect logTraceAspect(final LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }
}
