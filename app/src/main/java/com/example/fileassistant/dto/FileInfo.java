package com.example.fileassistant.dto;

import java.io.File;
import java.util.Map;

public class FileInfo {
    private String name;
    private boolean isFolder;
    private long lastModified;
    private File file;
    private Map<String, FileInfo> childFiles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void setFolder(boolean folder) {
        isFolder = folder;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Map<String, FileInfo> getChildFiles() {
        return childFiles;
    }

    public void setChildFiles(Map<String, FileInfo> childFiles) {
        this.childFiles = childFiles;
    }
}
