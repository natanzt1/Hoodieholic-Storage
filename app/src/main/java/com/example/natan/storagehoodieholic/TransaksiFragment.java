package com.example.natan.storagehoodieholic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.natan.storagehoodieholic.adapter.BarangAdapter;
import com.example.natan.storagehoodieholic.adapter.TransaksiAdapter;
import com.example.natan.storagehoodieholic.api.ApiClient;
import com.example.natan.storagehoodieholic.api.ApiService;
import com.example.natan.storagehoodieholic.helper.dbHelper;
import com.example.natan.storagehoodieholic.model.Barang;
import com.example.natan.storagehoodieholic.model.Transaksi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiFragment extends Fragment {
    View view;
    RecyclerView rvTransaksi;
    private TransaksiAdapter adapter;
    private List<Transaksi> transaksis = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transaksi,container,false);
        rvTransaksi = view.findViewById(R.id.rv_transaksi);
        rvTransaksi.setLayoutManager(new LinearLayoutManager(getActivity()));
        callApi();
        return view;
    }

    private void callApi() {
        ApiService service = ApiClient.getService(getActivity());
//        Sistem mengambil data dari server pada koding di bawah ini
        service.getTransaksi()
                .enqueue(new Callback<List<Transaksi>>() {
                    @Override
                    public void onResponse(Call<List<Transaksi>> call, Response<List<Transaksi>> response) {
//                        jika sistem berhasil mengambil data dari server maka koding di bawah ini yg dijalankan
                        if (response.isSuccessful()){
                            transaksis = response.body();
//                            Kodingan di bawah ini yang menyimpan data Transaksi ke dalam SQLite
                            dbHelper dbHelper = new dbHelper(getActivity());
                            dbHelper.upgradeTransaksi();
                            for(Transaksi a_transaksi:transaksis){
                                insertTransaksi(dbHelper, a_transaksi);
                            }
                            setAdapter();
                        }else {
                            Toast.makeText(getActivity(), ""+response, Toast.LENGTH_SHORT).show();
                        }
                    }
                    //                  Koding ini yg dijalankan jika koneksi terputus
                    @Override
                    public void onFailure(Call<List<Transaksi>> call, Throwable t) {
                        dbHelper dbHelper = new dbHelper(getActivity());
                        Toast.makeText(getActivity(), "Koneksi Terputus", Toast.LENGTH_SHORT).show();
//                        Sistem mengambil data Transaksi dari SQLite
                        transaksis = dbHelper.selectTransaksi();
                        setAdapter();
                    }
                });
    }

//    setRecyclerView Transaksi
    private void setAdapter(){
        adapter = new TransaksiAdapter(getActivity(), transaksis);
        rvTransaksi.setAdapter(adapter);
    }

//    Insert data transaksi ke SQLite
    private void insertTransaksi(dbHelper dbHelper, Transaksi transaksi){
        int id = transaksi.getId();
        String nama_barang = transaksi.getNamaBarang();
        String nama_user = transaksi.getNamaUser();
        String updated_at = transaksi.getUpdatedAt();
        String gambar = transaksi.getGambar();
        int jumlah = transaksi.getJumlah();
        int id_barang = transaksi.getIdBarang();
        int id_user = transaksi.getUserId();
        dbHelper.insertTransaksi(id, id_barang, jumlah, id_user, updated_at, nama_user, nama_barang, gambar);
    }
}
