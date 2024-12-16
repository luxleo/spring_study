package study.advanced.threadlocal;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import study.advanced.threadlocal.code.FieldService;

@Slf4j
public class FieldServiceTest {
    FieldService fieldService = new FieldService();

    @Test
    void field_safe() {
        log.info("main start");
        Runnable userA = () -> {
            fieldService.logic("userA");
        };
        Runnable userB = () -> {
            fieldService.logic("userB");
        };
        Thread threadA = new Thread(userA, "thread-a");
        Thread threadB = new Thread(userB, "thread-b");

        threadA.start();
        sleep(2000);

        threadB.start();
        sleep(3000);

        log.info("main end");
    }

    @Test
    void field_race() {
        Runnable userA = () -> {
            fieldService.logic("userA");
        };
        Runnable userB = () -> {
            fieldService.logic("userB");
        };
        Thread threadA = new Thread(userA, "thread-a");
        Thread threadB = new Thread(userB, "thread-b");

        threadA.start();
        sleep(100);

        threadB.start();
        sleep(2000);
    }
    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
