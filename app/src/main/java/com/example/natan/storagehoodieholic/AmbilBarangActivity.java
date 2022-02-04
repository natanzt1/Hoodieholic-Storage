package com.example.natan.storagehoodieholic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.natan.storagehoodieholic.api.ApiClient;
import com.example.natan.storagehoodieholic.api.ApiService;
import com.example.natan.storagehoodieholic.model.ResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AmbilBarangActivity extends AppCompatActivity {
    Spinner spJumlah;
    TextView tvNama;
    ImageView ivImage;
    String nama, image, status;
    Button ambilBtn;
    int stok, id;
    ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambil_barang);
        setLayout(this);
    }

//    Koding setLayout berfungsi untuk mengatur tampilan dari halaman Ambil Barang
    public void setLayout(final Context context){
        spJumlah = findViewById(R.id.spinner_ambil_jumlah);
        ivImage = findViewById(R.id.img_ambil_image);
        tvNama = findViewById(R.id.tv_ambil_nama);
        ambilBtn = findViewById(R.id.ambil_submit_button);

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("idBarang");
        nama = extras.getString("namaBarang");
        image = extras.getString("imageBarang");
        stok = extras.getInt("stokBarang");


        tvNama.setText(nama);
        ambilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stok == 0){
                    Toast.makeText(context, "Barang Kosong", Toast.LENGTH_SHORT).show();
                    status = "unsuccessful";
                }
                else if(stok>0){
                    int jumlah = (int) spJumlah.getSelectedItem();
                    ambilDataSubmit(id, jumlah, context);
                }
            }
        });

//        set Image View
        Glide.with(this)
                .load("http://192.168.1.86:8000/api/barang/image/"+image)
                .into(ivImage);
        setSpinner(stok);
    }

    private void setSpinner(int stok) {
        List<Integer> arrayJumlah = new ArrayList<Integer>();
        int i;
        if(stok > 0){
            for(i = 0; i<stok; i++){
                arrayJumlah.add(i+1);
            }
        }
        else if(stok == 0){
            arrayJumlah.add(0);
        }

        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, arrayJumlah);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spJumlah.setAdapter(dataAdapter);
    }

//    Koding ini dijalankan saat tombol submit ditekan
    public void ambilDataSubmit(int id, int jumlah, final Context context){
        service = ApiClient.getService(this);
        Toast.makeText(context, ""+id, Toast.LENGTH_SHORT).show();
//        Koding di bawah ini dijalankan untuk menambah data transaksi ambil barang ke server
        service.ambilBarang(id, jumlah)
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context, "Ambil Barang berhasil", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(context, "Gagal mengambil barang", Toast.LENGTH_SHORT).show();
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
