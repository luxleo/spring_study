package study.advanced_proxy.config.v5_autoproxy;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import study.advanced_proxy.config.AppConfigV1;
import study.advanced_proxy.config.AppConfigV2;
import study.advanced_proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import study.advanced_proxy.trace.logtrace.LogTrace;

/**
 * <p>자동 프록시 생성기 - AutoProxyCreator
 * 앞서 이야기한 스프링 부트 자동 설정으로 AnnotationAwareAspectJAutoProxyCreator 라는 빈 후처리
 * 기가 스프링 빈에 자동으로 등록된다.
 * 이름 그대로 자동으로 프록시를 생성해주는 빈 후처리기이다.</p>
 * 이 빈 후처리기는 스프링 빈으로 등록된 Advisor 들을 자동으로 찾아서 프록시가 필요한 곳에 자동으로 프록시
 * 를 적용해준다.
 * Advisor 안에는 Pointcut 과 Advice 가 이미 모두 포함되어 있다. 따라서 Advisor 만 알고 있으면 그 안
 * 에 있는 Pointcut 으로 어떤 스프링 빈에 프록시를 적용해야 할지 알 수 있다. 그리고 Advice 로 부가 기능을
 * 적용하면 된다.
 */
@Import({AppConfigV1.class,AppConfigV2.class})
@Configuration
public class AutoProxyConfig {
//    @Bean
    public Advisor advisor1(final LogTrace trace) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("save*","request*","order*");

        return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(trace));
    }

    /** -> no log일 때도 출력하는 문제를 가지고 있음
     * * : 모든 반환 타입
     * hello.proxy.app.. : 해당 패키지와 그 하위 패키지
     * *(..) : * 모든 메서드 이름, (..) 파라미터는 상관 없음
     * @param trace
     * @return
     */
//    @Bean
    public Advisor advisor2(final LogTrace trace) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* study.advanced_proxy.app..*(..)");

        return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(trace));
    }

    @Bean
    public Advisor advisor3(final LogTrace trace) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* study.advanced_proxy.app..*(..)) && !execution(* study.advanced_proxy.app..noLog(..))");

        return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(trace));
    }
}
