package com.android.promedcameraintent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "PROMEDCAMERAINTENT";
    final static int CAMERA_RESULT = 0;
    ImageView imv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(i);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        Log.d(TAG,"requestCode:" + requestCode + "resultCode:" + resultCode + "intent" + intent);

        if(resultCode == RESULT_OK) {
            Bundle extras = intent.getExtras();
            Bitmap bmp = (Bitmap)extras.get("data");
            imv = (ImageView)findViewById(R.id.ReturnedImageView);
            imv.setImageBitmap(bmp);
        }
    }
}
