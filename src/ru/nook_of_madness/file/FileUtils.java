package ru.nook_of_madness.file;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class FileUtils {
    public static String md5(File file) {
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            return DatatypeConverter.printHexBinary(MessageDigest.getInstance("MD5").digest(bytes));
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean equals(File file1, File file2) {
        try {
            return Arrays.equals(Files.readAllBytes(file1.toPath()), Files.readAllBytes(file2.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static long size(File file) {
        return file.length();
    }
}
