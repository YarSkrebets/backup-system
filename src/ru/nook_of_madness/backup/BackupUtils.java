package ru.nook_of_madness.backup;

import java.io.File;

public class BackupUtils {
    public static Backup createBackup(File dir, BackupType backupType) {
        Backup backup = new Backup(backupType);
        backup.addFiles(dir.listFiles(file -> !file.isDirectory() && !file.getName().equals("test")));
        return backup;
    }


}
