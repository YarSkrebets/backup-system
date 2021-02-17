package ru.nook_of_madness.backup;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Backup {
    private List<File> originalFiles = new ArrayList<>();
    private List<BackupPoint> backupPoints = new ArrayList<>();
    private BackupType backupType;

    public Backup(BackupType backupType) {
        this.backupType = backupType;
    }

    public List<File> getOriginalFiles() {
        return originalFiles;
    }

    public void addFiles(File... files) {
        originalFiles.addAll(Arrays.asList(files));
    }

    public List<BackupPoint> getBackupPoints() {
        return backupPoints;
    }

    public BackupPoint getLastBackupPoint() {
        return backupPoints.size() == 0 ? null : backupPoints.get(backupPoints.size() - 1);
    }

    public void addPoint(BackupPoint point) {
        backupPoints.add(point);
    }

    public void removePoint(BackupPoint point) {
        backupPoints.remove(point);
    }

    public void printFileList() {
        System.out.println("Backup files:");
        originalFiles.stream().forEach(file -> System.out.println(file.getAbsolutePath()));
    }

    public BackupType getBackupType() {
        return backupType;
    }

    public void setBackupType(BackupType backupType) {
        this.backupType = backupType;
    }
}
