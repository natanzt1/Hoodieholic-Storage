package com.example.natan.storagehoodieholic;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.natan.storagehoodieholic.api.ApiClient;
import com.example.natan.storagehoodieholic.api.ApiService;
import com.example.natan.storagehoodieholic.model.Detection;
import com.example.natan.storagehoodieholic.model.ResponseModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetectionResultActivity extends AppCompatActivity {
    private static final String TAG = "SAVE";
    ImageView ivGambar;
    TextView tvHasil;
    Button backBtn, saveBtn;
    MultipartBody.Part image_upload;
    String image, hasil;
    public static final int MY_PERMISSIONS_REQUEST_STORAGE= 1;
    private static final int IMG_REQUEST = 100;
    ApiService service;

//    Program mulai berjalan dari sini
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Masuk ke ","DETECTION RESULT");
        setContentView(R.layout.activity_detection_result);
        setLayout(this);
    }


    public void setLayout(final Context context){
        ivGambar = findViewById(R.id.img_detection_result);
        saveBtn = findViewById(R.id.detect_save_button);
        backBtn = findViewById(R.id.detect_back_button);
        tvHasil = findViewById(R.id.tv_detect_result);

        ivGambar.setImageResource(0);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backFunction();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFunction();
            }
        });
        Bundle extras = getIntent().getExtras();
        image = extras.getString("image");
        hasil = extras.getString("hasil");
        Log.i("ISI URL IMAGE ",""+image);
        Log.i("ISI KETERANGAN DETEKSI ",""+hasil);
        tvHasil.setText(hasil);
//        set Image View

        Glide.with(this)
                .load("http://192.168.1.86:8000/detection_result/"+image)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(ivGambar);
    }

    public void backFunction(){
        // Fungsi untuk kembali ke intent sebelumnya
        finish();
    }

    public void saveFunction(){
        File dest = Environment.getExternalStorageDirectory();
        Drawable drawable = ivGambar.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        String fileName = String.format("%d.jpg", System.currentTimeMillis());
        FileOutputStream outStream = null;
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/Download");
        Log.i(TAG, "Dir: "+dir);
        dir.mkdirs();
        File outFile = new File(dir, fileName);
        Toast.makeText(DetectionResultActivity.this, "Mencoba Save",Toast.LENGTH_SHORT).show();
        try {
            Toast.makeText(DetectionResultActivity.this, "Save Progress",Toast.LENGTH_SHORT).show();
            outStream = new FileOutputStream(outFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        try {
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
