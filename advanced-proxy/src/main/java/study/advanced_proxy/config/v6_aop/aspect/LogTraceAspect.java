package study.advanced_proxy.config.v6_aop.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import study.advanced_proxy.trace.TraceStatus;
import study.advanced_proxy.trace.logtrace.LogTrace;

@Slf4j
@RequiredArgsConstructor
@Aspect
public class LogTraceAspect {

    private final LogTrace trace;

    @Around("execution(* study.advanced_proxy.app..*(..))")
    public Object execute(final ProceedingJoinPoint joinPoint) throws Exception {
        TraceStatus status = null;
        try {
            String message = joinPoint.getSignature().toShortString();
            status = trace.begin(message);
            Object result = joinPoint.proceed();
            trace.end(status);
            return result;
        } catch (Throwable e) {
            if (e instanceof Exception) {
                trace.exception(status, (Exception) e);
            }
            throw new Exception(e);
        }
    }
}
