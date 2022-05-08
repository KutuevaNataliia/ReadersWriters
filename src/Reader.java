import java.io.IOException;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class Reader implements Runnable {

    private final CountingSemaphore semaphore;
    private int tries = 10;
    private final Logger logger;

    public Reader(CountingSemaphore semaphore) throws IOException {
        this.semaphore = semaphore;
        LoggerWrapper.getInstance();
        logger = LoggerWrapper.logger;
    }

    @Override
    public void run() {
        while (tries > 0) {
            semaphore.acqRead();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Reader interrupted");
            }
            logger.info("Reading");
            semaphore.relRead();
            tries--;
        }
    }
}
