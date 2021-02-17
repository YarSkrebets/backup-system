package ru.nook_of_madness.conditions;

import ru.nook_of_madness.backup.Backup;
import ru.nook_of_madness.backup.BackupPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HybridDeleteCondition implements DeleteCondition {
    private List<DeleteCondition> deleteConditions;
    private HybridType hybridType;

    public HybridDeleteCondition(HybridType hybridType, DeleteCondition... deleteConditions) {
        this.hybridType = hybridType;
        this.deleteConditions = Arrays.asList(deleteConditions);
    }

    @Override
    public List<BackupPoint> findToRemovePoint(Backup backup) {
        if (hybridType == HybridType.ANY) {
            return deleteConditions.stream().flatMap(deleteCondition -> deleteCondition.findToRemovePoint(backup).stream()).
                    distinct().collect(Collectors.toList());
        } else {
            if (deleteConditions.size() > 0) {
                List<BackupPoint> backupPoints = new ArrayList<>(deleteConditions.get(0).findToRemovePoint(backup));
                for (DeleteCondition deleteCondition : deleteConditions) {
                    List<BackupPoint> toRemovePoint = deleteCondition.findToRemovePoint(backup);
                    backupPoints.removeIf(point -> !toRemovePoint.contains(point));

                }
                return backupPoints;
            } else {
                return Collections.EMPTY_LIST;
            }
        }
    }
}
