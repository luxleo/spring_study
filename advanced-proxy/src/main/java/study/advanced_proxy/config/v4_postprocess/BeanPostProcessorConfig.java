package study.advanced_proxy.config.v4_postprocess;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import study.advanced_proxy.config.AppConfigV1;
import study.advanced_proxy.config.AppConfigV2;
import study.advanced_proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import study.advanced_proxy.config.v4_postprocess.postprocess.PackageLogTracePostProcess;
import study.advanced_proxy.trace.logtrace.LogTrace;

@Slf4j
@Import({AppConfigV1.class, AppConfigV2.class})
@Configuration
public class BeanPostProcessorConfig {
    @Bean
    public PackageLogTracePostProcess logTracePostProcess(final LogTrace trace) {
        final String PACKAGE_BASE = "study.advanced_proxy.app";
        return new PackageLogTracePostProcess(getAdvisor(trace), PACKAGE_BASE);
    }

    private Advisor getAdvisor(final LogTrace trace) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("save*", "request*", "order*");

        return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(trace));
    }
}
