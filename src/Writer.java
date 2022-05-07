import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class Writer implements Runnable {

    private CountingSemaphore semaphore;
    private AtomicInteger tries = new AtomicInteger(7);

    public Writer(CountingSemaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        while (tries.get() > 0) {
            try {
                //sleep(200);
                semaphore.acqWrite();
                sleep(5000);
                semaphore.relWrite();
                tries.getAndDecrement();
            } catch (InterruptedException e) {
                System.out.println("Writer interrupted");
            }
        }
    }
}
