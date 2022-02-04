package com.example.natan.storagehoodieholic.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.natan.storagehoodieholic.AmbilBarangActivity;
import com.example.natan.storagehoodieholic.EditBarangActivity;
import com.example.natan.storagehoodieholic.R;
import com.example.natan.storagehoodieholic.RegisterActivity;
import com.example.natan.storagehoodieholic.model.Transaksi;

import java.util.List;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.ViewHolder>{
    private Context context;
    private List<Transaksi> transaksis;
    private static final String TAG = "TAG RECYCLER";

    public TransaksiAdapter(Context context, List<Transaksi> transaksis) {
        this.context = context;
        this.transaksis = transaksis;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgBarang;
        TextView tvNamaBarang, tvNamaUser, tvTanggal, tvJumlah, tvKet1, tvKet2, tvKet3;
        public ViewHolder(@NonNull View view) {
            super(view);
            imgBarang = view.findViewById(R.id.iv_transaksi_barang);
            tvNamaBarang =view.findViewById(R.id.tv_transaksi_nama_barang);
            tvNamaUser = view.findViewById(R.id.tv_transaksi_nama_user);
            tvTanggal = view.findViewById(R.id.tv_transaksi_tanggal);
            tvJumlah = view.findViewById(R.id.tv_transaksi_jumlah);
            tvKet1 = view.findViewById(R.id.tv_transaksi_ket1);
            tvKet2 = view.findViewById(R.id.tv_transaksi_ket2);
            tvKet3 = view.findViewById(R.id.tv_transaksi_ket3);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.list_transaksi,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        Transaksi transaksi = transaksis.get(i);
        viewHolder.tvNamaBarang.setText(transaksi.getNamaBarang());
        viewHolder.tvNamaUser.setText(transaksi.getNamaUser());
        viewHolder.tvKet1.setText("Jumlah: ");
        viewHolder.tvKet2.setText(" Pcs");
        viewHolder.tvKet3.setText("Tanggal : ");
        viewHolder.tvJumlah.setText(String.valueOf(transaksi.getJumlah()));
        viewHolder.tvTanggal.setText(transaksi.getUpdatedAt());

        Glide.with(context)
                .load("http://192.168.1.86:8000/api/barang/image/"+transaksi.getGambar())
                .into(viewHolder.imgBarang);
//        Toast.makeText(context, ""+transaksi.getGambar(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return transaksis.size();
    }

}

