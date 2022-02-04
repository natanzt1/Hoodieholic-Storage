package com.example.natan.storagehoodieholic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.natan.storagehoodieholic.api.ApiClient;
import com.example.natan.storagehoodieholic.api.ApiService;
import com.example.natan.storagehoodieholic.model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditBarangActivity extends AppCompatActivity {
    private static final String TAG = "Tes Edit Barang";
    EditText etNama, etStok;
    ImageView ivImage;
    String nama, image, status;
    Button editBtn;
    int stok, id;
    ApiService service;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_barang);
        setLayout(this);
    }

    public void setLayout(final Context context){
        ivImage = findViewById(R.id.iv_edit_image);
        etNama = findViewById(R.id.et_edit_nama);
        etStok = findViewById(R.id.et_edit_stok);
        editBtn = findViewById(R.id.barang_edit_submit_button);

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("idBarang");
        nama = extras.getString("namaBarang");
        image = extras.getString("imageBarang");
        stok = extras.getInt("stokBarang");

        etNama.setText(nama);
        etStok.setText(""+stok);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = etNama.getText().toString();
                int stok = Integer.parseInt(etStok.getText().toString());
                editDataSubmit(id, nama, stok, EditBarangActivity.this);
            }
        });

//        set Image View
        Glide.with(this)
                .load("http://192.168.1.86:8000/api/barang/image/"+image)
                .into(ivImage);
    }

//    Koding ini dijalankan saat Tombol submit ditekan
    public void editDataSubmit(int id, String nama, int stok, final Context context){
        service = ApiClient.getService(this);
        Toast.makeText(context, ""+id, Toast.LENGTH_SHORT).show();
//        Koding ini dijalankan untuk menyimpan data transaksi ke server
        service.editBarang(id, nama, stok)
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context, "Ambil Data berhasil", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(context, "Gagal mengirim data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Toast.makeText(context, "Error: "+t, Toast.LENGTH_SHORT).show();
                    }
                });
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}

