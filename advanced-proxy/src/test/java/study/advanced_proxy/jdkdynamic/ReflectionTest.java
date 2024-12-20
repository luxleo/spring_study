package study.advanced_proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {
    @Test
    void reflection0() {
        Hello target = new Hello();

        log.info("start");
        String result1 = target.callA();
        log.info("result={}",result1);

        log.info("start");
        String result2 = target.callB();
        log.info("result={}",result2);
    }

    @DisplayName("리플렉션 이용하여 동적 처리하기")
    @Test
    void reflection1()
            throws ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException
    {
        // 클래스 정보 획득
        Class<?> helloClass = Class.forName("study.advanced_proxy.jdkdynamic.ReflectionTest.Hello");

        Hello target = new Hello();

        // 클래스 정보로 부터 메서드 획득
        Method methodCallA = helloClass.getMethod("callA");
        Object result1 = methodCallA.invoke(target);
        log.info("result1={}",result1);

        Method methodCallB = helloClass.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result2={}",result2);
    }

    @DisplayName("리플렉션 이용하여 동적 처리하기")
    @Test
    void reflection2()
            throws ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException
    {
        // 클래스 정보 획득
        Class<?> helloClass = Class.forName("study.advanced_proxy.jdkdynamic.ReflectionTest.Hello");

        Hello target = new Hello();

        // 클래스 정보로 부터 메서드 획득
        Method methodCallA = helloClass.getMethod("callA");
        dynamicCall(methodCallA,target);

        Method methodCallB = helloClass.getMethod("callB");
        dynamicCall(methodCallB,target);
    }

    private void dynamicCall(Method method, Object target)
            throws InvocationTargetException, IllegalAccessException
    {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result={}",result);
    }

    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }
        public String callB() {
            log.info("callB");
            return "B";
        }
    }

}
