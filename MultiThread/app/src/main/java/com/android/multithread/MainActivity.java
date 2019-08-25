package com.android.multithread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/*Android studio 多线程网络文件下载*/

public class MainActivity extends AppCompatActivity {

    EditText editText_url;
    ListView listView;
    TextView textView;
    ProgressBar progressBar;

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> localImages = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    MyAsyncTask asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList.add("http://img2.imgtn.bdimg.com/it/u=500808421,1575925585&fm=200&gp=0.jpg");
        arrayList.add("http://img1.imgtn.bdimg.com/it/u=2883138594,3332343437&fm=27&gp=0.jpg");
        arrayList.add("http://img0.imgtn.bdimg.com/it/u=2779717376,1807907918&fm=200&gp=0.jpg");
        arrayList.add("http://img0.imgtn.bdimg.com/it/u=416447706,3692155938&fm=27&gp=0.jpg");
        arrayList.add("http://img3.imgtn.bdimg.com/it/u=2058241956,1502683125&fm=27&gp=0.jpg");
        arrayList.add("http://img0.imgtn.bdimg.com/it/u=2779717376,1807907918&fm=200&gp=0.jpg");
        arrayList.add("http://img0.imgtn.bdimg.com/it/u=2779717376,1807907918&fm=200&gp=0.jpg");

        editText_url = findViewById(R.id.editText_url);
        listView = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simp)

    }
}
