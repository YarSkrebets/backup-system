package ru.nook_of_madness;

import ru.nook_of_madness.backup.Backup;
import ru.nook_of_madness.backup.BackupPoint;
import ru.nook_of_madness.backup.BackupType;
import ru.nook_of_madness.backup.BackupUtils;
import ru.nook_of_madness.conditions.HybridDeleteCondition;
import ru.nook_of_madness.conditions.HybridType;
import ru.nook_of_madness.conditions.LengthDeleteCondition;
import ru.nook_of_madness.conditions.SizeDeleteCondition;
import ru.nook_of_madness.exception.StrongPointBackupException;
import ru.nook_of_madness.strategy.ApointBackupFileLocationStrategy;
import ru.nook_of_madness.strategy.BackupFileLocationStrategy;
import ru.nook_of_madness.strategy.BackupStrategy;
import ru.nook_of_madness.strategy.BackupStrategyImpl;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        BackupFileLocationStrategy backupFileLocationStrategy = new ApointBackupFileLocationStrategy(new File("backups"));
        BackupStrategy backupStrategy = new BackupStrategyImpl(backupFileLocationStrategy);

        File originalFolder = new File("test_original");
        Backup backup = BackupUtils.createBackup(originalFolder, BackupType.INCREMENTAL);
        backup.printFileList();

        BackupPoint backupPoint = backupStrategy.createRestorePoint(backup);
        System.out.println(backupPoint.toString());
        BackupPoint emptyBackupPoint = backupStrategy.createRestorePoint(backup);
        System.out.println(emptyBackupPoint.toString());

        backup.addFiles(new File(originalFolder, "test"));
        BackupPoint newFilePoint = backupStrategy.createRestorePoint(backup);
        System.out.println(newFilePoint);

        System.out.println();
        System.out.println();

        HybridDeleteCondition deleteCondition = new HybridDeleteCondition(HybridType.ANY, new LengthDeleteCondition(1), new SizeDeleteCondition(100000));
        deleteCondition.findToRemovePoint(backup).forEach(point -> {
            try {
                backupStrategy.removeRestorePoint(point);
            } catch (StrongPointBackupException e) {
                e.printStackTrace();
            }
        });
    }
}
