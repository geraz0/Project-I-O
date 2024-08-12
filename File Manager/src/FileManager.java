import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.File;

public class FileManager {

    public boolean handleUserChoice(int choice, Scanner scanner) throws IOException {
        Path path, source, destination;
        switch (choice) {
            case 1: // Display directory contents
                System.out.print("Enter directory path to list contents: ");
                path = Paths.get(scanner.nextLine());
                listDirectoryContents(path);
                break;
            case 2: // Copy file
                System.out.print("Enter source file path to copy: ");
                source = Paths.get(scanner.nextLine());
                System.out.print("Enter destination file path: ");
                destination = Paths.get(scanner.nextLine());
                copyFile(source, destination);
                System.out.println("File copied successfully.");
                break;
            case 3: // Move file
                System.out.print("Enter source file path to move: ");
                source = Paths.get(scanner.nextLine());
                System.out.print("Enter destination path: ");
                destination = Paths.get(scanner.nextLine());
                moveFile(source, destination);
                System.out.println("File moved successfully.");
                break;
            case 4: // Delete file
                System.out.print("Enter file path to delete: ");
                path = Paths.get(scanner.nextLine());
                deleteFile(path);
                System.out.println("File deleted successfully.");
                break;
            case 5: // Create directory
                System.out.print("Enter directory path to create: ");
                path = Paths.get(scanner.nextLine());
                createDirectory(path);
                System.out.println("Directory created successfully.");
                break;
            case 6: // Delete directory
                System.out.print("Enter directory path to delete: ");
                path = Paths.get(scanner.nextLine());
                deleteDirectory(path);
                System.out.println("Directory deleted successfully.");
                break;
            case 7: // Search files
                System.out.print("Enter directory path to search in: ");
                path = Paths.get(scanner.nextLine());
                System.out.print("Enter search pattern (e.g., '*.txt'): ");
                String pattern = scanner.nextLine();
                searchFiles(path, pattern);
                break;
            case 8: // Create file
                System.out.print("Enter file path to create: ");
                path = Paths.get(scanner.nextLine());
                createFile(path);
                break;
            default:
                System.out.println("Invalid choice.");
                return false;
        }
        return true;
    }

    private void listDirectoryContents(Path dirPath) throws IOException {
        try (Stream<Path> stream = Files.walk(dirPath, 1)) {
            stream.forEach(filePath -> {
                try {
                    BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
                    System.out.println("File: " + filePath + " Size: " + attrs.size() + " Last Modified: " + attrs.lastModifiedTime());
                } catch (IOException e) {
                    System.out.println("Error reading file attributes: " + e.getMessage());
                }
            });
        }
    }

    private void copyFile(Path source, Path destination) throws IOException {
        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    private void moveFile(Path source, Path destination) throws IOException {
        Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
    }

    private void deleteFile(Path path) throws IOException {
        Files.deleteIfExists(path);
    }

    private void createDirectory(Path path) throws IOException {
        Files.createDirectories(path);
    }

    private void deleteDirectory(Path path) throws IOException {
        Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    private void searchFiles(Path dirPath, String pattern) throws IOException {
        // Correct the glob pattern syntax if necessary
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);

        try (Stream<Path> stream = Files.walk(dirPath)) {
            List<Path> foundFiles = stream.filter(matcher::matches).collect(Collectors.toList());
            foundFiles.forEach(System.out::println);

            if (foundFiles.isEmpty()) {
                System.out.println("No files found matching: " + pattern);
            }
        }
    }

    private void createFile(Path path) throws IOException {
        if (Files.exists(path)) {
            System.out.println("File already exists: " + path);
        } else {
            Files.createFile(path);
            System.out.println("File created successfully: " + path);
        }
    }
}
