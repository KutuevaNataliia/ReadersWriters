import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerWrapper {

    public static final Logger logger = Logger.getLogger("CommonLogger");
    private static LoggerWrapper instance = null;

    public static LoggerWrapper getInstance() throws IOException {
        if (instance == null) {
            prepareLogger();
            instance = new LoggerWrapper();
        }
        return instance;
    }

    private static void prepareLogger() throws IOException {
        FileHandler fileHandler = new FileHandler("mylog.txt");
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);
        logger.setUseParentHandlers(false);
    }
}
