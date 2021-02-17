package ru.nook_of_madness.strategy;

import ru.nook_of_madness.backup.BackupPoint;

import java.io.File;

public class TogetherBackupFileLocationStrategy implements BackupFileLocationStrategy {
    private File dir;

    public TogetherBackupFileLocationStrategy(File dir) {
        this.dir = dir;
        this.dir.mkdirs();
    }

    @Override
    public File resolve(File originalFile, BackupPoint point) {
        return new File(dir, originalFile.getName() + point.getName());
    }
}
