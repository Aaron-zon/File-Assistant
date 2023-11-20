package com.example.fileassistant.utils;

import android.content.Context;

import com.example.fileassistant.FolderAssistant;
import com.example.fileassistant.dto.FileInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileManage {

    // 当前上下文对象
    private Context mContext;

    public FileManage(Context context) {
        this.mContext = context;
    }

    public Map<String, FileInfo> getFilesAndFolders(String directoryPath) {
        Map<String, FileInfo> fileTree = new HashMap<>();
        File directory = new File(directoryPath);
        if (directory.exists()) {
            fileTree = this.getAllFilesAndFolders(directory);
        }
        return fileTree;
    }

    private Map<String, FileInfo> getAllFilesAndFolders(File directory) {
        Map<String, FileInfo> fileTree = new HashMap<>();
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setName(file.getName());
                    fileInfo.setLastModified(file.lastModified());

                    // 判断是文件还是文件夹
                    if (file.isDirectory()) { // 文件夹
                        fileInfo.setFolder(true);
                        fileInfo.setChildFiles(this.getAllFilesAndFolders(file));
                    } else { // 文件
                        fileInfo.setFolder(false);
                        fileInfo.setFile(file);
                    }

                    fileTree.put(file.getName(), fileInfo);
                }
            }
        }
        return fileTree;
    }


}
