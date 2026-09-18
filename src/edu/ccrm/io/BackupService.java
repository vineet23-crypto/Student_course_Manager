package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupService {

    public Path backupData(Path exportDir, Path backupRoot) throws IOException {
        // Validate inputs
        if (!Files.exists(exportDir) || !Files.isDirectory(exportDir)) {
            throw new IOException("Export directory does not exist: " + exportDir);
        }

        Files.createDirectories(backupRoot);

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path backupDir = backupRoot.resolve("backup_" + timestamp);
        Files.createDirectories(backupDir);

        int fileCount = 0;
        long totalSize = 0;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(exportDir)) {
            for (Path file : stream) {
                if (Files.isRegularFile(file)) {
                    long fileSize = Files.size(file);
                    Files.copy(file, backupDir.resolve(file.getFileName()),
                            StandardCopyOption.REPLACE_EXISTING);
                    fileCount++;
                    totalSize += fileSize;
                }
            }
        }

        System.out.printf("Backup created: %s (%d files, %d bytes)%n",
                backupDir.getFileName(), fileCount, totalSize);
        return backupDir;
    }

    public long computeFolderSize(Path dir) throws IOException {
        if (!Files.exists(dir)) {
            System.out.println("Directory does not exist: " + dir);
            return 0;
        }

        if (!Files.isDirectory(dir)) {
            System.out.println("Path is not a directory: " + dir);
            return 0;
        }

        final long[] size = {0};
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                size[0] += attrs.size();
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                System.err.println("Error accessing file: " + file + " - " + exc.getMessage());
                return FileVisitResult.CONTINUE;
            }
        });

        System.out.println("Computed size of " + dir + ": " + size[0] + " bytes");
        return size[0];
    }

    // Utility method to format size in human-readable format
    public String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        String pre = "KMGTPE".charAt(exp-1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }
}