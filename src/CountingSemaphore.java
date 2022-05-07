import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static java.lang.Thread.sleep;

public class CountingSemaphore {

    public static final int maxCount = Integer.MAX_VALUE / 2;
    private final AtomicInteger counter = new AtomicInteger(maxCount);
    private Logger logger;

    public CountingSemaphore() throws IOException {
        logger = Logger.getLogger(CountingSemaphore.class.getName());
        Handler[] handlers = logger.getHandlers();
        if (handlers.length > 0 && handlers[0] instanceof ConsoleHandler) {
            logger.removeHandler(handlers[0]);
        }
        FileHandler fileHandler = new FileHandler("mylog.txt");
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);
    }

    public void acqRead() {
        acquire(1);
    }

    public void relRead() {
        release(1);
    }

    public void acqWrite() {
        acquire(maxCount);
    }

    public void relWrite() {
        release(maxCount);
    }

    private void acquire(int k) {
        try {
            while (counter.get() <= 0) {
                sleep(0);
            }
            //сюда приходят все 3 читателя и 1 писатель практически одновременно
            logger.info("Захват семафора на " + k);
            synchronized (CountingSemaphore.class) {
                counter.set(counter.get() - k);
                CountingSemaphore.class.notifyAll();
            }
            //все по очереди уменьшают счётчик
            logger.info("Выход из критической секции " + counter.get());
            //и теперь никто из них не может выйти
            while (counter.get() < 0) {
                sleep(0);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }
    }

    private void release(int k) {
        logger.info("Попытка освобождения семафора");
        synchronized (CountingSemaphore.class) {
            counter.set(counter.get() + k);
            CountingSemaphore.class.notifyAll();
        }
        logger.info("Освобождение семафора на " + k);
    }
}
