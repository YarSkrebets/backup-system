package ru.nook_of_madness.conditions;

import ru.nook_of_madness.backup.Backup;
import ru.nook_of_madness.backup.BackupPoint;

import java.util.List;

public interface DeleteCondition {
    List<BackupPoint> findToRemovePoint(Backup backup);
}
