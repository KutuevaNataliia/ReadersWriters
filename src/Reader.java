import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class Reader implements Runnable {

    private CountingSemaphore semaphore;
    private AtomicInteger tries = new AtomicInteger(10);

    public Reader(CountingSemaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        while (tries.get() > 0) {
            semaphore.acqRead();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Reader interrupted");
            }
            semaphore.relRead();
            tries.getAndDecrement();
        }
    }
}
