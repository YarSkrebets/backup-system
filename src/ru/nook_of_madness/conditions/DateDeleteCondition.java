package ru.nook_of_madness.conditions;

import ru.nook_of_madness.backup.Backup;
import ru.nook_of_madness.backup.BackupPoint;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DateDeleteCondition implements DeleteCondition {
    private Predicate<Date> deletePredicate;

    public DateDeleteCondition(Predicate<Date> deletePredicate) {
        this.deletePredicate = deletePredicate;
    }

    @Override
    public List<BackupPoint> findToRemovePoint(Backup backup) {
        return backup.getBackupPoints().stream().filter(BackupPoint::isImportantForFuture)
                .filter(point -> deletePredicate.test(point.getTime()))
                .collect(Collectors.toList());
    }
}
