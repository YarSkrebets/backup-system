package ru.nook_of_madness.conditions;

import ru.nook_of_madness.backup.Backup;
import ru.nook_of_madness.backup.BackupPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SizeDeleteCondition implements DeleteCondition {
    private long maxSize;

    public SizeDeleteCondition(long maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public List<BackupPoint> findToRemovePoint(Backup backup) {
        long currentSize = backup.getBackupPoints().stream().mapToLong(BackupPoint::getSize).sum();
        if (currentSize > maxSize) {
            List<BackupPoint> toDelete = new ArrayList<>();
            for (BackupPoint point : backup.getBackupPoints()) {
                if (!point.isImportantForFuture()) {
                    toDelete.add(point);
                    currentSize -= point.getSize();
                }
                if (currentSize <= maxSize) {
                    break;
                }
            }
            if (currentSize > maxSize) {
                System.out.println("Предупреждение: Не удалось уменьшить размер до приемлимового");
            }
            return toDelete;
        } else {
            return Collections.EMPTY_LIST;
        }
    }
}
