package ru.nook_of_madness.backup;

import ru.nook_of_madness.file.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BackupPoint {
    private String name = UUID.randomUUID().toString().replaceFirst("-", "").substring(0, 15);
    private Backup root;
    private List<File> backupFiles;
    private Date time;
    private long size = -1;

    private boolean dependsOnPrevious;
    private boolean importantForFuture;

    public BackupPoint(Backup root, List<File> backupFiles, Date time, long size) {
        this.root = root;
        this.backupFiles = backupFiles;
        this.time = time;
        this.size = size;
    }

    public BackupPoint(Backup root, List<File> backupFiles, Date time) {
        this.root = root;
        this.backupFiles = backupFiles;
        this.time = time;
    }

    public Backup getRoot() {
        return root;
    }

    public List<File> getBackupFiles() {
        return backupFiles;
    }

    public Date getTime() {
        return time;
    }

    public long getSize() {
        if (size == -1) {
            this.size = backupFiles.stream().mapToLong(FileUtils::size).sum();
        }
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isDependsOnPrevious() {
        return dependsOnPrevious;
    }

    public boolean isImportantForFuture() {
        return importantForFuture;
    }


    public void setImportantForFuture(boolean importantForFuture) {
        this.importantForFuture = importantForFuture;
    }

    public void setDependsOnPrevious(boolean dependsOnPrevious) {
        this.dependsOnPrevious = dependsOnPrevious;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "BackupPoint{" +
                "name='" + name + '\'' +
                ", root=" + root +
                ", backupFiles=" + backupFiles +
                ", time=" + time +
                ", size=" + getSize() +
                ", dependsOnPrevious=" + dependsOnPrevious +
                ", importantForFuture=" + importantForFuture +
                '}';
    }
}
