import java.io.IOException;
import java.util.logging.Logger;

public class CountingSemaphore {

    public static final int MAX_COUNT = Integer.MAX_VALUE / 2;
    private final Object lockObj = new Object();
    private int counter = MAX_COUNT;
    private final Logger logger;

    public CountingSemaphore() throws IOException {
        LoggerWrapper.getInstance();
        logger = LoggerWrapper.logger;
    }

    public void acqRead() {
        acquire(1);
    }

    public void relRead() {
        release(1);
    }

    public void acqWrite() {
        acquire(MAX_COUNT);
    }

    public void relWrite() {
        release(MAX_COUNT);
    }

    private void acquire(int k) {
        try {
            synchronized (lockObj) {
                while (counter <= 0) {
                    lockObj.wait();
                }
                counter -= k;
                logger.info("Захват семафора на " + k);
                while (counter < 0) {
                    lockObj.wait();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }

    private void release(int k) {
        //logger.info("Попытка освобождения семафора");
        synchronized (lockObj) {
            counter += k;
            lockObj.notifyAll();
        }
        logger.info("Освобождение семафора на " + k);
    }
}
