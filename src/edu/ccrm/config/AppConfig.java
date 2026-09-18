package edu.ccrm.config;
import java.nio.file.Files;
import java.nio.file.Path;
public final class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();
    // Defaults relative to project root
    private Path importDir = Path.of("imports");
    private Path exportDir = Path.of("exports");
    private Path backupRoot = Path.of("backups");

    private AppConfig() {
        // Ensure directories exist
        safeMkdir(importDir);
        safeMkdir(exportDir);
        safeMkdir(backupRoot);
    }

    public static AppConfig get() { return INSTANCE; }

    public Path getImportDir() { return importDir; }
    public Path getExportDir() { return exportDir; }
    public Path getBackupRoot() { return backupRoot; }

    public void setImportDir(Path p) { this.importDir = ensureDir(p); }
    public void setExportDir(Path p) { this.exportDir = ensureDir(p); }
    public void setBackupRoot(Path p) { this.backupRoot = ensureDir(p); }

    private void safeMkdir(Path p) {
        try { Files.createDirectories(p); } catch (Exception ignored) {}
    }
    private Path ensureDir(Path p) {
        safeMkdir(p);
        return p;
    }
}