package ru.nook_of_madness.strategy;

import ru.nook_of_madness.backup.Backup;
import ru.nook_of_madness.backup.BackupPoint;
import ru.nook_of_madness.backup.BackupType;
import ru.nook_of_madness.exception.StrongPointBackupException;
import ru.nook_of_madness.file.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BackupStrategyImpl implements BackupStrategy {
    private BackupFileLocationStrategy backupFileLocationStrategy;

    public BackupStrategyImpl(BackupFileLocationStrategy backupFileLocationStrategy) {
        this.backupFileLocationStrategy = backupFileLocationStrategy;
    }

    @Override
    public BackupPoint createRestorePoint(Backup backup) {
        if (backup.getBackupType() == BackupType.FULLY) {
            //ООО повезло повезло
            return createFullyBackupPoint(backup);
        } else {
            if (backup.getBackupPoints().size() == 0) {
                //Тут создаем полный бекап(тсс)
                return createFullyBackupPoint(backup);
            } else {
                List<File> files = new ArrayList<>();
                BackupPoint backupPoint = new BackupPoint(backup, files, new Date());
                BackupPoint previousPoint = backup.getLastBackupPoint();
                List<File> previousFiles = previousPoint.getBackupFiles();
                List<File> currentFiles = backup.getOriginalFiles();
                if (previousFiles.size() != backup.getOriginalFiles().size()) {
                    return createFullyBackupPoint(backup);
                }
                boolean dependsOnPrevious = false;
                for (int i = 0; i < previousFiles.size(); i++) {
                    if (FileUtils.equals(previousFiles.get(i), currentFiles.get(i))) {
                        dependsOnPrevious = true;
                        files.add(previousFiles.get(i));
                    } else {
                        File backupFile = backupFileLocationStrategy.resolve(currentFiles.get(i), backupPoint);
                        try {
                            Files.copy(currentFiles.get(i).toPath(), backupFile.toPath());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        files.add(backupFile);
                    }
                }
                backupPoint.setDependsOnPrevious(dependsOnPrevious);
                previousPoint.setImportantForFuture(previousPoint.isImportantForFuture() || dependsOnPrevious);
                return backupPoint;
            }
        }
    }

    private BackupPoint createFullyBackupPoint(Backup backup) {
        List<File> files = new ArrayList<>();
        BackupPoint point = new BackupPoint(backup, files, new Date());

        for (File file : backup.getOriginalFiles()) {
            File backupFile = backupFileLocationStrategy.resolve(file, point);

            try {
                Files.copy(file.toPath(), backupFile.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            files.add(backupFile);
        }
        backup.addPoint(point);
        return point;
    }

    @Override
    public void removeRestorePoint(BackupPoint point) throws StrongPointBackupException {
        if ((point.getRoot().getBackupType() == BackupType.FULLY) || (!point.isImportantForFuture())) {
            //Повезло)
            point.getBackupFiles().forEach(File::delete);
            point.getRoot().removePoint(point);
        } else {
            // Грустно становится. Вообще нам надо просто начать обыскивать но это слишком затратно поэтому просто экзепшен
            throw new StrongPointBackupException();
        }
    }
}
