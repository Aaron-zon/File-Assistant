package com.example.fileassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fileassistant.dto.FileInfo;
import com.example.fileassistant.utils.FileHelper;
import com.example.fileassistant.utils.FileManage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FolderAssistant extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 301;
    public Button btn;
    // 返回按钮
    public Button prev_btn;
    public ListView listView;
    // 排序方式
    public int sortMode = 0;
    // 当前目录
    public List<String> nowPath = new LinkedList<>();
    // 文件集合
    Map<String, FileInfo> fileTree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder_assistant);
        // 隐藏header
        getSupportActionBar().hide();
        // 获取权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
        }

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);

        prev_btn = findViewById(R.id.prev_btn);
        prev_btn.setOnClickListener(this);

        listView = findViewById(R.id.list_view);
        listView.setOnItemClickListener(this);
    }

    private void showFolder() {
        Map<String, FileInfo> newFileTree = fileTree;
        if (nowPath.size() > 0) {
            for (String path : nowPath) {
                newFileTree = newFileTree.get(path).getChildFiles();
            }
        }
        List<String> nowFileList = getNowFiles(newFileTree);
        this.sortFileList(nowFileList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(FolderAssistant.this,android.R.layout.simple_list_item_1, nowFileList);
        listView.setAdapter(adapter);
    }

    public List<String> getNowFiles(Map<String, FileInfo> fileTree) {
        List<String> result = new ArrayList<>();
        if (fileTree == null || fileTree.isEmpty()) {
            return result;
        }
        for (String key : fileTree.keySet()) {
            result.add(key);
        }

        return result;
    }

    public List<String> sortFileList(List<String> fileList) {
        // folder
        // file

        // name
        if (this.sortMode == 0) {
            // Collections.sort(folder);
            // Collections.sort(file);
            // folder.addAll(file)
        } else if (this.sortMode == 1) {
            // modified

        } else if (this.sortMode == 2) {
            // type
        }

        return fileList;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String result = ((TextView)view).getText().toString();
        nowPath.add(result);
        showFolder();
    }

    @Override
    public void onClick(View v) {
        int id = ((Button)v).getId();
        if (id == R.id.btn) {
            FileManage fileManage = new FileManage(FolderAssistant.this);
            this.fileTree = fileManage.getFilesAndFolders("/sdcard");
            this.showFolder();
        } else if (id == R.id.prev_btn) {
            if (nowPath.size() > 0) {
                nowPath.remove(nowPath.size() - 1);
                showFolder();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 读取外部存储的权限已获得，可以执行相应的操作
                    Log.d("MainActivity", "权限：读取成功");
                } else {
                    // 读取外部存储的权限未获得，可以执行相应的处理
                    Log.d("MainActivity", "权限：读取失败");
                }
                break;
            default:
                break;
        }
    }
}