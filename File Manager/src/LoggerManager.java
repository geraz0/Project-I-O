import java.io.IOException;
import java.util.logging.*;

public class LoggerManager {
    public static final Logger LOGGER = Logger.getLogger(LoggerManager.class.getName());

    static {
        setupLogging();
    }

    private static void setupLogging() {
        try {
            FileHandler fileHandler = new FileHandler("fileManager.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Failed to initialize log handler: " + e.getMessage());
        }
    }
}
