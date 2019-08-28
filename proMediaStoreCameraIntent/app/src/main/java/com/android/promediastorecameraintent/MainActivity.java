package com.android.promediastorecameraintent;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MEDIASTORE_CAMERAINTENT";
    final static int CAMERA_RESULT = 0;

    final static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0xF4;

    Uri imageFileUri;

    ImageView returnImageView;
    Button takePictureButton;
    Button saveDataButton;
    TextView titleTextView;
    TextView descriptionTextView;
    EditText titleEditText;
    EditText descriptionEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        returnImageView = (ImageView)findViewById(R.id.ReturnImageView);
        takePictureButton = (Button)findViewById(R.id.TakePictureButton);
        saveDataButton = (Button)findViewById(R.id.SaveDataButton);
        titleTextView = (TextView)findViewById(R.id.TitleTextView);
        descriptionTextView = (TextView)findViewById(R.id.DescriptionTextView);
        titleEditText = (EditText)findViewById(R.id.TitleEditText);
        descriptionEditText = (EditText)findViewById(R.id.DescriptionEditText);


        returnImageView.setVisibility(View.GONE);
        //takePictureButton.setVisibility(View.GONE);
        saveDataButton.setVisibility(View.GONE);
        titleTextView.setVisibility(View.GONE);
        descriptionTextView.setVisibility(View.GONE);
        titleEditText.setVisibility(View.GONE);
        descriptionEditText.setVisibility(View.GONE);


        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG,"takePictureButton.setOnClickListener-->11");


                if(ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        Log.d(TAG,"takePictureButton.setOnClickListener-if->?<-");

                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                        Log.d(TAG,"takePictureButton.setOnClickListener-else->?<-");
                    }
                }


                imageFileUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());

                Log.d(TAG,"takePictureButton.setOnClickListener-->22");

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                Log.d(TAG,"takePictureButton.setOnClickListener-->33");

                i.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);

                Log.d(TAG,"takePictureButton.setOnClickListener-->44");

                startActivityForResult(i, CAMERA_RESULT);

                Log.d(TAG,"takePictureButton.setOnClickListener-->55");

            }
        });

        saveDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues(3);
                contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, titleEditText.getText().toString());
                contentValues.put(MediaStore.Images.Media.DESCRIPTION, descriptionEditText.getText().toString());
                getContentResolver().update(imageFileUri, contentValues, null, null);

                Toast bread = Toast.makeText(MainActivity.this, "Record Updated!", Toast.LENGTH_SHORT);
                bread.show();

                takePictureButton.setVisibility(View.VISIBLE);

                returnImageView.setVisibility(View.GONE);
                saveDataButton.setVisibility(View.GONE);
                titleTextView.setVisibility(View.GONE);
                descriptionTextView.setVisibility(View.GONE);
                titleEditText.setVisibility(View.GONE);
                descriptionEditText.setVisibility(View.GONE);

            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        if( resultCode == RESULT_OK ) {
            takePictureButton.setVisibility(View.GONE);

            returnImageView.setVisibility(View.VISIBLE);
            saveDataButton.setVisibility(View.VISIBLE);
            titleTextView.setVisibility(View.VISIBLE);
            descriptionTextView.setVisibility(View.VISIBLE);
            titleEditText.setVisibility(View.VISIBLE);
            descriptionEditText.setVisibility(View.VISIBLE);

            int dw = 200;
            int dh = 200;


            //int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

//            if(ContextCompat.checkSelfPermission(MainActivity.this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    != PackageManager.PERMISSION_GRANTED ) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//
//                    Log.d(TAG,"onActivityResult-if->requestCode="+requestCode);
//
//                } else {
//                    ActivityCompat.requestPermissions(MainActivity.this,
//                            new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
//                    Log.d(TAG,"onActivityResult-else->requestCode="+requestCode);
//                }
//            }


            try {
                BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                bmpFactoryOptions.inJustDecodeBounds = true;
                Bitmap bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageFileUri), null, bmpFactoryOptions);

                int heightRatio = (int)Math.ceil(bmpFactoryOptions.outHeight/(float)dh);
                int widthRatio = (int)Math.ceil(bmpFactoryOptions.outWidth/(float)dw);

                Log.d(TAG,"onActivityResult-->HEIGHTRATIO:"+heightRatio);
                Log.d(TAG, "onActivityResult-->WIDTHRATIO:"+widthRatio);

                if( heightRatio>1 && widthRatio>1 ) {
                    if( heightRatio > widthRatio ) {
                        bmpFactoryOptions.inSampleSize = heightRatio;
                    } else {
                        bmpFactoryOptions.inSampleSize = widthRatio;
                    }
                }

                bmpFactoryOptions.inJustDecodeBounds = false;

                bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageFileUri), null , bmpFactoryOptions);

                returnImageView.setImageBitmap(bmp);

            } catch (FileNotFoundException e) {
                Log.e(TAG, e.toString());
            }
        }
    }

    //ActivityCompat.OnRequestPermissionsResultCallback

    @Override
    public void onRequestPermissionsResult(int requestCode, String permission[], int [] grantResults) {

        Log.d(TAG,"onRequestPermissionsResult-->requestCode="+requestCode);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(MainActivity.this,"权限允许...", Toast.LENGTH_SHORT).show();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this,"权限禁止...", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request

        }
        super.onRequestPermissionsResult(requestCode, permission, grantResults);
    }
}
