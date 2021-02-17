package ru.nook_of_madness.strategy;

import ru.nook_of_madness.backup.BackupPoint;

import java.io.File;

public interface BackupFileLocationStrategy {
    File resolve(File originalFile, BackupPoint point);


}
