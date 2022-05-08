import java.io.IOException;;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class Writer implements Runnable {

    private final CountingSemaphore semaphore;
    private int tries = 7;
    private final Logger logger;

    public Writer(CountingSemaphore semaphore) throws IOException {
        this.semaphore = semaphore;
        LoggerWrapper.getInstance();
        logger = LoggerWrapper.logger;
    }

    @Override
    public void run() {
        while (tries > 0) {
            semaphore.acqWrite();
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Writer interrupted");
            }
            logger.info("Writing");
            semaphore.relWrite();
            tries--;
        }
    }
}
