package study.advanced_proxy.app.v2;

public class OrderRepositoryV2 {
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
