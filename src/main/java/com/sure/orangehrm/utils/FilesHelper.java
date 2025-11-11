package com.sure.orangehrm.utils;

import java.nio.file.Paths;

public class FilesHelper {
    public static String generateFilePath(String fileName) {
        String relativefilePath = "src/test/resources/files/"+fileName;

        return Paths.get(relativefilePath).toAbsolutePath().toString();
}}