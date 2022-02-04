package com.example.natan.storagehoodieholic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.natan.storagehoodieholic.adapter.BarangAdapter;
import com.example.natan.storagehoodieholic.api.ApiClient;
import com.example.natan.storagehoodieholic.api.ApiService;
import com.example.natan.storagehoodieholic.helper.dbHelper;
import com.example.natan.storagehoodieholic.model.Barang;
import com.example.natan.storagehoodieholic.service.MyFirebaseInstanceIdService;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton addItemBtn;
    private RecyclerView rvBarang;
    private BarangAdapter adapter;
    Button threedotsBtn;
    ApiService service;

    private static final String TAG = "MainActivity";

    private List<Barang> barangs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Sistem utama menggunakan tampilan fragment, tombol fragment ada pada bagian bawah aplikasi
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
//        Fragment yang aktif dicek pada koding bottomNav.OnNavigationItemSelectedListener
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        setTitle("Jaket");

        service = ApiClient.getService(this);
        rvBarang=findViewById(R.id.rv_barang);
        threedotsBtn = findViewById(R.id.rv_threedots);
        rvBarang.setLayoutManager(new LinearLayoutManager(this));
        callApi();
    }

//    Fragment yang dijalankan akan dicek pada kodingan ini.
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
//  Khusus Fragment home, kodingan yg dijalankan terdapat pada MainActivity pada fungsi callApi() di bawah koding ini
//  Misal user menekan tombol fragment Add, maka tampilan pada AddFragment yang dijalankan
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;

//                            Kodingan ini yg dijalankan jika tombol "Add" yang ditekan
                        case R.id.nav_add:
                            selectedFragment = new AddFragment();
                            MyFirebaseInstanceIdService firebaseInstanceIdService = new MyFirebaseInstanceIdService();
                            firebaseInstanceIdService.onTokenRefresh();
                            Log.i("TOKENNYA", ""+FirebaseInstanceId.getInstance().getToken());
                            break;

//                            Kodingan ini yg dijalankan jika tombol "Transaksi" yang ditekan
                        case R.id.nav_transaksi:
                            selectedFragment = new TransaksiFragment();
                            break;

//                            Kodingan ini yg dijalankan jika tombol Account yang ditekan
                        case R.id.nav_account:
                            selectedFragment = new AccountFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

//    Koding yang dijalankan saat homefragment yang dibuka
    private void callApi() {
//        Sistem mengambil data dari server pada koding di bawah ini
        service.getBarang()
                .enqueue(new Callback<List<Barang>>() {
                    @Override
                    public void onResponse(Call<List<Barang>> call, Response<List<Barang>> response) {
//                        jika sistem berhasil mengambil data dari server maka koding di bawah ini yg dijalankan
                        if (response.isSuccessful()){
                            barangs = response.body();
//                            Kodingan di bawah ini yang menyimpan data barang ke dalam SQLite
                            dbHelper dbHelper = new dbHelper(MainActivity.this);
                            dbHelper.upgradeBarang();
                            for(Barang a_barang:barangs){
                                insertBarang(dbHelper, a_barang);
                            }
                            setAdapter();
                        }else {
                            Toast.makeText(MainActivity.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                        }
                    }
//                  Koding ini yg dijalankan jika koneksi terputus
                    @Override
                    public void onFailure(Call<List<Barang>> call, Throwable t) {
                        dbHelper dbHelper = new dbHelper(MainActivity.this);
                        Toast.makeText(MainActivity.this, "Koneksi Terputus", Toast.LENGTH_SHORT).show();
//                        Sistem mengambil data barang dari SQLite
                        barangs = dbHelper.selectBarang();
                        setAdapter();
                    }
                });
    }

//    Koding ini dijalankan untuk menampilkan data barang pada recyclerview pada halaman utama
//    Silahkan copy koding dari folder adapter->BarangAdapter
    private void setAdapter(){
        adapter = new BarangAdapter(MainActivity.this, barangs);
        rvBarang.setAdapter(adapter);
    }

//  Koding ini untuk menambah data barang yang didapat dari server ke SQLite
    private void insertBarang(dbHelper dbHelper, Barang barang){
        int id = barang.getId();
        String nama = barang.getNamaBarang();
        int stok = barang.getStok();
        String image = barang.getImage();
        dbHelper.insertBarang(id, nama, stok, image);
    }

    public void onDestroy(){
        super.onDestroy();
    }

}
