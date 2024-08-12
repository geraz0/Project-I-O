package org.example;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

public class FileSystemWithFileStore {
    // Get the default FileSystem
    FileSystem fileSystem = FileSystems.getDefault();

    // Create a Path object using the FileSystem
    Path filePath = fileSystem.getPath("demo.txt");

    try {
        // 1. Create the file if it doesn't exist
        if (Files.notExists(filePath)) {
            Files.createFile(filePath);
            System.out.println("File created: " + filePath);
//            System.out.println("File created: " + filePath.toAbsolutePath());
        } else {
            System.out.println("File already exists: " + filePath);
        }

        // 2. Retrieve and display FileStore information
        FileStore fileStore = Files.getFileStore(filePath);
        System.out.println("\nFile Store Information:");
        System.out.println("Type: " + fileStore.type());
        System.out.println("Total Space: " + fileStore.getTotalSpace() + " bytes");
        System.out.println("Usable Space: " + fileStore.getUsableSpace() + " bytes");
        System.out.println("Unallocated Space: " + fileStore.getUnallocatedSpace() + " bytes");
        System.out.println("Is Read-Only: " + fileStore.isReadOnly());

        // 3. Retrieve Basic File Attributes
        BasicFileAttributes basicAttrs = Files.readAttributes(filePath, BasicFileAttributes.class); //.class is representing a class object --> you are passing what type of class I am going to be using for the basic attributes types (e.g. POSIX)
        System.out.println("\nBasic File Attributes:");
        System.out.println("Creation Time: " + basicAttrs.creationTime());
        System.out.println("Last Modified Time: " + basicAttrs.lastModifiedTime());
        System.out.println("Size: " + basicAttrs.size() + " bytes");
        System.out.println("Is Directory: " + basicAttrs.isDirectory());
        System.out.println("Is RegularFile: " + basicAttrs.isRegularFile());

        // 4. Modify the Last Modified Time
        FileTime newLastModifiedTime = FileTime.fromMillis(System.currentTimeMillis());
        Files.setLastModifiedTime(filePath, newLastModifiedTime);
        System.out.println("\nUpdated Last Modified Time: " + Files.getLastModifiedTime(filePath));

        // 5. Retrieve and modify POSIX file permissions (only on POSIX-compliant systems)
        if (fileSystem.supportedFileAttributeViews().contains("posix")) {
            PosixFileAttributes posixAttrs = Files.readAttributes(filePath, PosixFileAttributes.class);
            Set<PosixFilePermission> permissions = posixAttrs.permissions();

            System.out.println("\nPOSIX File Permissions:");
            for (PosixFilePermission permission : permissions) {
                System.out.println(permission);
            }

            // Modify the file permissions (e.g., add owner write permission)
            permissions.add(PosixFilePermission.OWNER_WRITE);
            Files.setPosixFilePermissions(filePath, permissions);
            System.out.println("\nUpdated POSIX File Permissions:");
            for (PosixFilePermission permission : Files.getPosixFilePermissions(filePath)) {
                System.out.println(permission);
            }
        } else {
            System.out.println("\nPOSIX file permissions not supported on this system.");
        }

    } catch (IOException e) {
        System.out.println("An error occurred: " + e.getMessage());
    } finally {
        // Clean up by deleting the file
        try {
            Files.deleteIfExists(filePath);
            System.out.println("\nFile deleted: " + filePath);
        } catch (IOException e) {
            System.out.println("Error deleting the file: " + e.getMessage());

        }
    }
}
