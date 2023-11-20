package com.example.fileassistant.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {
    public static List<FileModel> getFilesAndFolders(String directoryPath) {
        List<FileModel> fileList = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.exists()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    FileModel fileModel = new FileModel(file.getName(), file.getAbsolutePath(), file.isDirectory());
                    fileList.add(fileModel);
                }
            }
        }

        return fileList;
    }

    public static class FileModel {
        private String name;
        private String path;
        private boolean isDirectory;

        public FileModel(String name, String path, boolean isDirectory) {
            this.name = name;
            this.path = path;
            this.isDirectory = isDirectory;
        }

        public String getName() {
            return name;
        }

        public String getPath() {
            return path;
        }

        public boolean isDirectory() {
            return isDirectory;
        }
    }
}
