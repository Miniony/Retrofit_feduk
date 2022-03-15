package com.example.smart_test;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class upload_image extends AppCompatActivity {
   FloatingActionButton Timepicker;
   Button chek_box;
   Button retrofit;
   Button recycler;


    private static final int IMG_REQUEST =  1;

//  Select image from gallery: ImageView
    ImageView imageToUpload;

//  Image upload: Button
    Button uploadBtn;

//  Variable to store gallery image path: String
    String profileImgPath;

//  Converting gallery image to bitmap to set it to imageview
    Bitmap bitmap;

//  Alert box
    private AlertDialog.Builder builder;

//  SERVER URL
    String UPLOAD_URL = "http://192.168.137.232:3000";

    @Override
    protected void onStart() {
        getPermissions();

        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_image);

        recycler = findViewById(R.id.recycler);
        recycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Recycler_view.class));
            }
        });

//        retrofit = findViewById(R.id.retrofit);
//        retrofit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(),retrofit.class));
//
//
//            }
//        });
        chek_box = findViewById(R.id.checkbox);
        chek_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Check_box.class));

            }
        });

        Timepicker = findViewById(R.id.add_person_fab);
        Timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Timepicker.class));
            }
        });

//      initialize vars
        initVars();

//      image view on click listener
        imageToUpload.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, IMG_REQUEST);
        });


//      upload btn on click listener
        uploadBtn.setOnClickListener(v-> {

            startActivity(new Intent(getApplicationContext(), change_image.class));
            if(isEmpty(profileImgPath)) {

                displayAlert("Please select a profile picture");
                return;
            }

            Ion.with(this)
                    .load(UPLOAD_URL)
                    .setMultipartFile("image", "image/jpeg", new File(profileImgPath))
                    .asJsonObject()
                    .withResponse()
                    .setCallback((e, result) -> {

                        if(e != null) {
                            Toast.makeText(this, "Error is: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }else {
                            switch (result.getHeaders().code()) {
                                case 500:
                                    Toast.makeText(this, "Image Uploading Failed. Unknown Server Error!", Toast.LENGTH_SHORT).show();
                                    break;
                                case 200:
                                    Toast.makeText(this, "Image Successfully Uploaded!", Toast.LENGTH_SHORT).show();
                                    Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                                            R.drawable.ic_baseline_send_24);
                                    imageToUpload.setImageBitmap(bitmap);
                                    profileImgPath = null;
                                    break;
                            }
                        }

                    });

        });

    }

    private void initVars() {
        builder = new AlertDialog.Builder(this);
        imageToUpload = findViewById(R.id.imagetoupload);
        uploadBtn = findViewById(R.id.uploadbtn);
    }

    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            if( requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
                Uri path = data.getData();
                if(path != null) {
                    profileImgPath = FetchPath.getPath(this, path);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                        imageToUpload.setImageBitmap(getCroppedBitmap(bitmap));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }

    public static boolean isEmpty(String field) {

        return field == null || field.isEmpty();
    }

    public void displayAlert(String message) {

        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id=item.getItemId();

        if (item_id==R.id.android){
            Toast.makeText(this, "This is android option item", Toast.LENGTH_SHORT).show();


        }
        else if (item_id==R.id.profile){
            Toast.makeText(this, "This is profile item", Toast.LENGTH_SHORT).show();
        }
        else if (item_id==R.id.download){
            Toast.makeText(this, "This is download item", Toast.LENGTH_SHORT).show();
        }
        else if (item_id==R.id.setting){
            Toast.makeText(this, "This is setting item", Toast.LENGTH_SHORT).show();
        }
        else if (item_id==R.id.exit){
            Toast.makeText(this, "This is exit item", Toast.LENGTH_SHORT).show();
        }
        else if (item_id==R.id.nav_search){
            Toast.makeText(this, "This is search item", Toast.LENGTH_SHORT).show();
        }

        return true;



    }
}

