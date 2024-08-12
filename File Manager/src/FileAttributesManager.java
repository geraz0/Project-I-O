import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.*;

public class FileAttributesManager {

    public static void printFileAttributes(Path path) throws IOException {
        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        LoggerManager.LOGGER.info("File Attributes for: " + path);
        LoggerManager.LOGGER.info("Creation Time: " + attrs.creationTime());
        LoggerManager.LOGGER.info("Last Modified Time: " + attrs.lastModifiedTime());
        LoggerManager.LOGGER.info("Size: " + attrs.size());
        LoggerManager.LOGGER.info("Is Directory: " + attrs.isDirectory());
        LoggerManager.LOGGER.info("Is Regular File: " + attrs.isRegularFile());
    }
}
