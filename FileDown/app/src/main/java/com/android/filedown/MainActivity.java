package com.android.filedown;

/* Android studio 简单的多线程 */

//import androidx.appcompat.app.AppCompatActivity;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import java.lang.ref.WeakReference;
//
//public class MainActivity extends AppCompatActivity {
//
//    private static final String TAG = "MainActivity";
//
//    private TextView textview;
//    private ProgressBar progressBar;
//    private MyAsyncTask myAsyncTask;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        textview = findViewById(R.id.textView);
//        progressBar = findViewById(R.id.progressBar);
//    }
//
//    static class MyAsyncTask extends AsyncTask<Integer, Integer,Integer> {
//
//        WeakReference<MainActivity> weakReference;
//
//        public MyAsyncTask(MainActivity activity) {
//            weakReference = new WeakReference<MainActivity>(activity);
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            MainActivity activity = weakReference.get();
//            if(activity != null) {
//                activity.progressBar.setProgress(0);
//            }
//            Log.d(TAG, "onPreExecute");
//        }
//
//        @Override
//        protected Integer doInBackground(Integer... integers) {
//            int sum = 0;
//            for(int i=1; i<100; i++) {
//                try {
//                    Log.d(TAG, "doInBackground:" + Thread.currentThread().getName());
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                sum += i;
//                publishProgress(i);
//
//                if(isCancelled()) {
//                    break;
//                }
//            }
//            return -1;
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            Log.d(TAG, "onProgressUpdate:");
//            MainActivity activity = weakReference.get();
//
//            if(activity != null) {
//                activity.textview.setText("progress"+values[0]);
//                activity.progressBar.setProgress(values[0]);
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//            MainActivity activity = weakReference.get();
//            if(activity != null) {
//                activity.textview.setText("cancel!!!");
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Integer integer) {
//            super.onPostExecute(integer);
//            Log.d(TAG,"onPostExecute");
//            MainActivity activity = weakReference.get();
//            if(activity != null) {
//                activity.textview.setText("congratulation!!!Finished!");
//                activity.progressBar.setProgress(0);
//            }
//        }
//    }
//
//    public void calculate(View v) {
//        myAsyncTask = new MyAsyncTask(this);
//        myAsyncTask.execute(0);
//    }
//
//    public void stop(View v){
//        myAsyncTask.cancel(true);
//    }
//}







import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.security.PublicKey;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView textView;
    ProgressBar progressBar;
    MyAsyncTask myAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar);
    }

    static class MyAsyncTask extends AsyncTask<Integer,Integer,Integer>{

        WeakReference<MainActivity> weakReference;

        public MyAsyncTask(MainActivity activity){
            weakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity activity = weakReference.get();
            if (activity!=null) activity.progressBar.setProgress(0);
            Log.d(TAG, "onPreExecute: ");
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            int sum = 0;
            for(int i = 1; i < 100; i++) {
                try {
                    Log.d(TAG, "doInBackground: " + Thread.currentThread().getName());
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sum += i;
                publishProgress(i);
                if(isCancelled()) break;
            }
            return -1;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d(TAG, "onProgressUpdate: ");
            MainActivity activity = weakReference.get();
            if (activity!=null) {
                activity.textView.setText("progress"+values[0]);
                activity.progressBar.setProgress(values[0]);

            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            MainActivity activity = weakReference.get();
            if (activity!=null) activity.textView.setText("cancel!!!!");
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Log.d(TAG, "onPostExecute: ");
            MainActivity activity = weakReference.get();
            if (activity!=null) {
                activity.textView.setText("congratulation!!!finished");
                activity.progressBar.setProgress(0);
            }
        }
    }


    public void calculate(View v) {
        myAsyncTask = new MyAsyncTask(this);
        myAsyncTask.execute(0);
    }

    public void stop(View v){
        myAsyncTask.cancel(true);
    }


//    private MyHandler handler = new MyHandler(this);
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            switch (msg.what){
//                case 0:
//                    textView.setText("progress:"+msg.arg1);
//
//                    break;
//                case 1:
//                    textView.setText("congratulation!!!finished"+msg.what);
//
//                    break;
//            }
//            Log.d(TAG, "handleMessage: "+Thread.currentThread().getName());
//        }
//    };
//
//    static class MyHandler extends Handler{
//
//        WeakReference<MainActivity> weakReference;
//
//        public MyHandler(MainActivity activity){
//            this.weakReference = new WeakReference<MainActivity>(activity);
//        }
//
////        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            MainActivity activity = weakReference.get();
//            if (activity == null) return;;
//
//            switch (msg.what){
//                case 0:
//                    activity.textView.setText("progress:"+msg.arg1);
//
//                    break;
//                case 1:
//                    activity.textView.setText("congratulation!!!finished"+msg.what);
//
//                    break;
//            }
//            Log.d(TAG, "handleMessage: "+Thread.currentThread().getName());
//        }
//    }


//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int sum = 0;
//                for(int i = 1; i < 100; i++){
//                    try {
//                        Log.d(TAG, "run: "+Thread.currentThread().getName());
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    sum += i;
//                    final int j = i;
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            textView.setText("progress:"+j);
//                        }
//                    });
////                    Message msg = handler.obtainMessage();
////                    msg.what = 0;
////                    msg.arg1 = i;
////                    handler.sendMessage(msg);
//                }
//
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        textView.setText("congratulation!!!finished");
//                    }
//                });
////
////                Message msg = handler.obtainMessage();
////                msg.what = 1;
////                handler.sendMessage(msg);
//            }
//        }).start();
//
//
//
//    }
}