package study.advanced_proxy.pureproxy.decorator.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MessageDecorator implements Component{
    private final Component component;
    @Override
    public String operation() {
        log.info("MessageDecorator start");
        String decorString = "******";
        String rawResult = component.operation();
        String result = decorString + rawResult+ decorString;
        log.info("MessageDecorator end, before={}, after={}",rawResult,result);
        return result;
    }
}
