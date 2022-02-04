package com.example.natan.storagehoodieholic;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.natan.storagehoodieholic.adapter.DetectionAdapter;
import com.example.natan.storagehoodieholic.api.ApiClient;
import com.example.natan.storagehoodieholic.api.ApiService;
import com.example.natan.storagehoodieholic.helper.dbHelper;
import com.example.natan.storagehoodieholic.model.Barang;
import com.example.natan.storagehoodieholic.model.Detection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetectionActivity extends AppCompatActivity {
    ImageView ivGambar;
    Button submitBtn, cameraBtn, galleryBtn;
    MultipartBody.Part image_upload, test;
    Uri imageUri;
    private RecyclerView rvBarang;
    private DetectionAdapter adapter;
    private Detection detections;
    private List<Barang> barangs = new ArrayList<>();
    public static final int MY_PERMISSIONS_REQUEST_STORAGE = 1;
    private static final int IMG_REQUEST = 100;
    private static final int CAM_REQUEST = 1337;
    ApiService service;

    //    Program mulai berjalan dari sini
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);
        ivGambar = findViewById(R.id.img_detection_upload);
        galleryBtn = findViewById(R.id.detect_gallery_button);
        cameraBtn = findViewById(R.id.detect_camera_button);
        submitBtn = findViewById(R.id.detect_submit_button);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitFunction();
            }
        });
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });
    }


    //    Koding ini berfungsi untuk menyimpan perubahan data ke dalam server.
    public void submitFunction() {
        //        Kodingan pada uploadImage() dijalankan saat user akan mengupload gambar
//        uploadImage();
//      Kodingan CallApi() dijalankan untuk menyimpan perubahan data ke dalam server
        callApi();
    }

    public void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 1337);
    }

    //  Koding ini yang dijalankan untuk menyimpan data perubahan ke server.
    private void callApi() {
        RequestBody nama_barang = RequestBody.create(MediaType.parse("text/plain"), "halo");
//        Sistem mengambil data dari server pada koding di bawah ini
        if (image_upload != null) {
            service = ApiClient.getService(this);
            service.submitDetection(nama_barang, image_upload)
                    .enqueue(new Callback<Detection>() {
                        @Override
                        public void onResponse(Call<Detection> call, Response<Detection> response) {
                            //                        jika sistem berhasil mengambil data dari server maka koding di bawah ini yg dijalankan
                            if (response.isSuccessful()) {
                                detections = response.body();
                                String intentImage = detections.getImage();
                                String intentHasil = detections.getHasil();
                                Intent intent = new Intent(DetectionActivity.this, DetectionResultActivity.class);
                                intent.putExtra("image", intentImage);
                                intent.putExtra("hasil", intentHasil);
                                startActivity(intent);
                            } else {
                                Toast.makeText(DetectionActivity.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                            }
                        }

                        //                  Koding ini yg dijalankan jika koneksi terputus
                        @Override
                        public void onFailure(Call<Detection> call, Throwable t) {
                            Toast.makeText(DetectionActivity.this, "Koneksi Terputus", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("REQUEST CODE ", ":" + resultCode);
        if (requestCode == 100) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(DetectionActivity.this.getContentResolver(), selectedImage);
                ivGambar.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String filePath = getRealPathFromURI_API19(DetectionActivity.this, selectedImage);
            File fileImg = new File(filePath);

            RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileImg);
            image_upload = MultipartBody.Part.createFormData("image", fileImg.getName(), reqFile);
            Log.i("GAMBAR", "" + fileImg);
        }
        else if (requestCode == CAM_REQUEST){

            Bitmap image = (Bitmap) data.getExtras().get("data");
            ivGambar.setImageBitmap(image);

            File dest = Environment.getExternalStorageDirectory();
            Drawable drawable = ivGambar.getDrawable();
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            String fileName = String.format("%d.jpg", System.currentTimeMillis());
            FileOutputStream outStream = null;
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/Download");

            dir.mkdirs();
            File outFile = new File(dir, fileName);
            Toast.makeText(DetectionActivity.this, "Mencoba Save",Toast.LENGTH_SHORT).show();
            try {
                Toast.makeText(DetectionActivity.this, "Successfully",Toast.LENGTH_SHORT).show();
                outStream = new FileOutputStream(outFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            image.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
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
            Uri selectedImage = Uri.fromFile(outFile);
            String filePath = getRealPathFromURI_API19(DetectionActivity.this, selectedImage);
            File fileImg = new File(filePath);

            RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileImg);
            image_upload = MultipartBody.Part.createFormData("image", fileImg.getName(), reqFile);
//            Toast.makeText(DetectionActivity.this, "Kamera", Toast.LENGTH_SHORT).show();
        }
    }

    private String getRealPathFromURI_API19(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                Cursor cursor = null;

                try {
                    String[] s = {MediaStore.MediaColumns.DISPLAY_NAME};
                    cursor = context.getContentResolver().query(uri, s, null, null, null);
                    String filename = cursor.getString(0);
                    String path = Environment.getExternalStorageDirectory().toString() + "/Download/" + filename;
                    if (!TextUtils.isEmpty(path)) {
                        return path;
                    }
                } finally {
                    cursor.close();
                }

                String id = DocumentsContract.getDocumentId(uri);
                if (id.startsWith("raw:")) {
                    return id.replaceFirst(Pattern.quote("raw:"), "");
                }
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);

            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }

        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;

    }

    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(DetectionActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_GRANTED) {

                    } else {
                        Toast.makeText(DetectionActivity.this, "permision denied", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
            }
        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
