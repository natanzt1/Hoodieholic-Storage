package com.example.natan.storagehoodieholic;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.natan.storagehoodieholic.api.ApiClient;
import com.example.natan.storagehoodieholic.api.ApiService;
import com.example.natan.storagehoodieholic.model.ResponseModel;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFragment extends Fragment{
    public static final int MY_PERMISSIONS_REQUEST_STORAGE= 1;
    private static final int IMG_REQUEST = 100;
    Button addSubmitButton;
    MultipartBody.Part image_upload;
    ImageView ivAddImage;
    Uri imageUri;
    Bitmap image_barang;
    View view;
    EditText etNamaBarang, etStokBarang;
    String nama_barang, filePath;
    int stok_barang;
    ApiService service;
    File fileImg;
    SharedPreferences sharedPreferences;

    private static final String TAG = "PERCOBAAN";

    @Nullable
    @Override
//    Program berjalan mulai dari sini
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add,container,false);
        addSubmitButton = view.findViewById(R.id.add_submit_button);
        ivAddImage = view.findViewById(R.id.iv_add_barang);
        etNamaBarang = view.findViewById(R.id.et_nama_barang);
        etStokBarang = view.findViewById(R.id.et_stok_barang);
//        Kodingan pada uploadImage() dijalankan saat user akan mengupload gambar
        uploadImage();

//      Kodingan pada callAPI() AKAN dijalankan saat tombol submit ditekan
        submitAddBarang();
        return view;
    }


    public void uploadImage() {
        ivAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,100);
            }
        });
    }

    public void submitAddBarang() {
        addSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApi();
            }
        });
    }

    private void callApi() {
//        RequestBody nama = RequestBody.create(MultipartBody.FORM, etNamaBarang.getText().toString());
//        RequestBody stok = RequestBody.create(MultipartBody.FORM, etStokBarang.getText().toString());
        String nama = etNamaBarang.getText().toString();
        Integer stok = Integer.parseInt(etStokBarang.getText().toString());
        RequestBody nama_barang = RequestBody.create(MediaType.parse("text/plain"), ""+nama);
        RequestBody stok_barang = RequestBody.create(MediaType.parse("text/plain"), ""+stok);

//      sistem akan mengecek apakah user sudah memilih file yg diupload,
        if (image_upload != null){
            service = ApiClient.getService(getActivity());
//            Sistem akan menjalankan koding di bawah ini untuk mengupload data ke server
            service.tambahBarang(nama_barang, stok_barang, image_upload)
                    .enqueue(new Callback<ResponseModel>(){
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(getActivity(), "Data Suskses tersimpan",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(), "Gagal Menyimpan. "+response.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Toast.makeText(getActivity(), "Error:"+t, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else{
            Toast.makeText(getActivity(), "Pilih gambar yang akan diupload.", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100){
            Uri selectedImage=data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                ivAddImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String filePath = getRealPathFromURI_API19(getActivity(),selectedImage);
            File fileImg = new File(filePath);

            RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileImg);
            image_upload = MultipartBody.Part.createFormData("image", fileImg.getName(), reqFile);
            Log.i("GAMBAR", ""+fileImg);
        }
    }

    private String getRealPathFromURI_API19(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)){

            if (isExternalStorageDocument(uri)){
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)){
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }

            else if (isDownloadsDocument(uri)){
                Cursor cursor = null;

                try {
                    String[] s ={MediaStore.MediaColumns.DISPLAY_NAME};
                    cursor = context.getContentResolver().query(uri,s,null,null,null);
                    String filename = cursor.getString(0);
                    String path = Environment.getExternalStorageDirectory().toString()+"/Download/"+filename;
                    if (!TextUtils.isEmpty(path)){
                        return path;
                    }
                }finally {
                    cursor.close();
                }

                String id = DocumentsContract.getDocumentId(uri);
                if (id.startsWith("raw:")){
                    return id.replaceFirst(Pattern.quote("raw:"), "");
                }
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }

            else if (isMediaDocument(uri)){
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
        }

        else if ("content".equalsIgnoreCase(uri.getScheme())){
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
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_GRANTED){

                        uploadImage();
                    }else {
                        Toast.makeText(getActivity(), "permision denied", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
            }
        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
