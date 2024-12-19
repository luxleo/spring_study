package study.advanced_proxy.app.v1;

public class OrderRepositoryImplV1 implements OrderRepositoryV1{
    @Override
    public void save(String itemId) {
        if (itemId.equals("ex")) {
            throw new IllegalArgumentException("exception occurred");
        }
        sleep(1000);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
