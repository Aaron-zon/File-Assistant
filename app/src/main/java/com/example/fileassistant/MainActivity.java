package com.example.fileassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.fileassistant.utils.FileHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<FileHelper.FileModel> fileList = FileHelper.getFilesAndFolders("/sdcard");

        for (FileHelper.FileModel fileModel : fileList) {
            String fileType = fileModel.isDirectory() ? "文件夹" : "文件";
            String fileInfo = String.format("%s：%s", fileType, fileModel.getName());
            Log.d("FileList", fileInfo);
        }
    }
}