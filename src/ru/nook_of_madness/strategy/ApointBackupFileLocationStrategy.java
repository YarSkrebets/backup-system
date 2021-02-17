package ru.nook_of_madness.strategy;

import ru.nook_of_madness.backup.BackupPoint;

import java.io.File;

public class ApointBackupFileLocationStrategy implements BackupFileLocationStrategy {
    private File dir;

    public ApointBackupFileLocationStrategy(File dir) {
        this.dir = dir;
    }

    @Override
    public File resolve(File originalFile, BackupPoint point) {
        File backupDirectory = new File(dir, point.getName());
        backupDirectory.mkdirs();
        return new File(backupDirectory, originalFile.getName());
    }
}
