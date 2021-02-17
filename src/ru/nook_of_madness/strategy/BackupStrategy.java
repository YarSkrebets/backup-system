package ru.nook_of_madness.strategy;

import ru.nook_of_madness.backup.Backup;
import ru.nook_of_madness.backup.BackupPoint;
import ru.nook_of_madness.exception.StrongPointBackupException;

public interface BackupStrategy {
    BackupPoint createRestorePoint(Backup backup);

    void removeRestorePoint(BackupPoint point) throws StrongPointBackupException;
}
