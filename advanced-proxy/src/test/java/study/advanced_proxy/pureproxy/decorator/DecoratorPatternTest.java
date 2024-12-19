package study.advanced_proxy.pureproxy.decorator;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import study.advanced_proxy.pureproxy.decorator.code.*;

@Slf4j
public class DecoratorPatternTest {
    @Test
    void noDecoratorTest() {
        Component component = new RealComponent();
        DecoratorPatternClient client = new DecoratorPatternClient(component);

        client.execute();
    }

    @Test
    void decorator1() {
        Component component = new RealComponent();
        Component decorator = new MessageDecorator(component);

        DecoratorPatternClient client = new DecoratorPatternClient(decorator);
        client.execute();
    }
    @Test
    void decorator2() {
        Component component = new RealComponent();
        Component messageDecorator = new MessageDecorator(component);
        Component timeDecorator = new TimeDecorator(messageDecorator);

        DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator);
        client.execute();
    }
}
