package ru.nook_of_madness.conditions;

import ru.nook_of_madness.backup.Backup;
import ru.nook_of_madness.backup.BackupPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LengthDeleteCondition implements DeleteCondition {
    private int maximumSize;

    public LengthDeleteCondition(int maximumSize) {
        this.maximumSize = maximumSize - 1;
    }

    @Override
    public List<BackupPoint> findToRemovePoint(Backup backup) {
        if (backup.getBackupPoints().size() > maximumSize) {
            List<BackupPoint> toDelete = new ArrayList<>();
            int amount = backup.getBackupPoints().size() - maximumSize;
            for (BackupPoint point : backup.getBackupPoints()) {
                if ((!point.isImportantForFuture()) && (amount-- > 0)) {
                    toDelete.add(point);
                }
            }
            if (amount != 0) {
                System.out.println("Предупреждение: Не удалось очистить бекапы.");
            }
            return toDelete;

        } else {
            return Collections.EMPTY_LIST;
        }
    }
}
