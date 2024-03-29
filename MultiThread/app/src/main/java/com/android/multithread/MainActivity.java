package com.android.multithread;

import androidx.appcompat.app.AppCompatActivity;

//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Environment;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.ArrayList;
//
///*Android studio 多线程网络文件下载*/
//
//public class MainActivity extends AppCompatActivity {
//
//    EditText editText_url;
//    ListView listView;
//    TextView textView;
//    ProgressBar progressBar;
//
//    ArrayList<String> arrayList = new ArrayList<>();
//    ArrayList<String> localImages = new ArrayList<>();
//    ArrayAdapter<String> arrayAdapter;
//
//    MyAsyncTask asyncTask;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        arrayList.add("http://img2.imgtn.bdimg.com/it/u=500808421,1575925585&fm=200&gp=0.jpg");
//        arrayList.add("http://img1.imgtn.bdimg.com/it/u=2883138594,3332343437&fm=27&gp=0.jpg");
//        arrayList.add("http://img0.imgtn.bdimg.com/it/u=2779717376,1807907918&fm=200&gp=0.jpg");
//        arrayList.add("http://img0.imgtn.bdimg.com/it/u=416447706,3692155938&fm=27&gp=0.jpg");
//        arrayList.add("http://img3.imgtn.bdimg.com/it/u=2058241956,1502683125&fm=27&gp=0.jpg");
//        arrayList.add("http://img0.imgtn.bdimg.com/it/u=2779717376,1807907918&fm=200&gp=0.jpg");
//        arrayList.add("http://img0.imgtn.bdimg.com/it/u=2779717376,1807907918&fm=200&gp=0.jpg");
//
//        editText_url = findViewById(R.id.editText_url);
//        listView = findViewById(R.id.listView);
//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,arrayList);
//        listView.setAdapter(arrayAdapter);
//
//        textView = findViewById(R.id.textView);
//        progressBar = findViewById(R.id.progressBar);
//
//        asyncTask = new MyAsyncTask();
//
//    }
//
//    public void add(View view) {
//        arrayList.add(editText_url.getText().toString());
//        arrayAdapter.notifyDataSetChanged();
//    }
//
//    public void download(View view) {
//        switch(asyncTask.getStatus()) {
//            case PENDING:
//                asyncTask.execute(0);
//                break;
//            case RUNNING:
//                break;
//            case FINISHED:
//                asyncTask = new MyAsyncTask();
//                asyncTask.execute(0);
//                break;
//        }
//    }
//
//    class MyAsyncTask extends AsyncTask<Integer, Integer, Integer> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            localImages.clear();
//        }
//
//        @Override
//        protected Integer doInBackground(Integer... integers) {
//            //在另外的一个线程里
//            for(int i=0; i<arrayList.size(); i++) {
//                String s = arrayList.get(i);
//                try {
//                    URL url = new URL(s);
//                    URLConnection connection = url.openConnection();
//                    int size = connection.getContentLength();
//                    publishProgress(0, i+1, size);
//
//                    InputStream inputStream = connection.getInputStream();
//                    byte [] bytes = new byte[100];
//
//                    int len = -1;
//
//                    File file = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
//                    String filename = file.getAbsolutePath() + "/" + i + ".jpg";
//                    FileOutputStream fileOutputStream = new FileOutputStream(filename);
//
//                    while ((len=inputStream.read(bytes)) != -1 ) {
//                        fileOutputStream.write(bytes, 0, len);
//                        //Thread.sleep(1);
//                        publishProgress(1,len);
//                    }
//
//                    fileOutputStream.close();
//                    inputStream.close();
//                    localImages.add(filename);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//
//            switch (values[0]) {
//                case 0:
//                    textView.setText("正在下载第" + values[1] + "个文件！");
//                    progressBar.setMax(values[2]);
//                    progressBar.setProgress(0);
//                    break;
//                case 1:
//                    progressBar.incrementProgressBy(values[1]);
//                    break;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Integer integer) {
//            super.onPostExecute(integer);
//            textView.setText("下载完成！");
//            progressBar.setProgress(0);
//        }
//    }
//
//    public void showImages(View view) {
//        Intent intent = new Intent(this, Main2Activity.class);
//        intent.putExtra("images", localImages);
//        startActivity(intent);
//    }
//
//}


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    EditText editText_url;
    ListView listView;  //heap堆，可以被整个对象所有方法访问
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
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        textView = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar);

        asyncTask = new MyAsyncTask();
    }

    public void add(View view){
        arrayList.add(editText_url.getText().toString());
        arrayAdapter.notifyDataSetChanged();
    }

    public void download(View view){

        switch (asyncTask.getStatus()){
            case PENDING:
                asyncTask.execute(0);
                break;
            case RUNNING:
                break;
            case FINISHED:
                asyncTask = new MyAsyncTask();
                asyncTask.execute(0);
                break;
        }

    }

    class MyAsyncTask extends AsyncTask<Integer,Integer,Integer>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            localImages.clear();
        }


        @Override
        protected Integer doInBackground(Integer... integers) {
            //在另外一个线程里面！！！
            for(int i=0;i<arrayList.size();i++){
                String s = arrayList.get(i);
                try {
                    URL url = new URL(s);
                    URLConnection connection = url.openConnection();
                    int size = connection.getContentLength();  //图片大小

                    publishProgress(0,i+1,size);

                    InputStream inputStream = connection.getInputStream();  //流
                    byte[] bytes = new byte[100];
                    int len = -1;

                    //准备好外部存储文件
                    File file = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
                    String filename = file.getAbsolutePath()+"/"+i+".jpg";
                    FileOutputStream fileOutputStream = new FileOutputStream(filename);

                    while ((len=inputStream.read(bytes))!=-1){
                        fileOutputStream.write(bytes,0,len);
                        publishProgress(1,len);
                    }

                    fileOutputStream.close();
                    inputStream.close();

                    localImages.add(filename);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            switch (values[0]){
                case 0:
                    textView.setText("正在下载第"+values[1]+"个文件！！！");
                    progressBar.setMax(values[2]);
                    progressBar.setProgress(0);
                    break;
                case 1:
                    progressBar.incrementProgressBy(values[1]);
                    break;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            textView.setText("下载完成！！！");
            progressBar.setProgress(0);
        }
    }

    public void showImages(View view){

        Intent intent = new Intent(this,Main2Activity.class);

        intent.putExtra("images",localImages);

        startActivity(intent);

    }
}

