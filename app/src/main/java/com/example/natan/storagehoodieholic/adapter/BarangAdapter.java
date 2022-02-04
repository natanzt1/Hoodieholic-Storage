package com.example.natan.storagehoodieholic.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.natan.storagehoodieholic.MainActivity;
import com.example.natan.storagehoodieholic.R;
import com.example.natan.storagehoodieholic.api.ApiClient;
import com.example.natan.storagehoodieholic.api.ApiService;
import com.example.natan.storagehoodieholic.model.Barang;
import com.example.natan.storagehoodieholic.model.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.ViewHolder>{
    private Context context;
    private List<Barang> barangs;
    private static final String TAG = "TAG RECYCLER";

    public BarangAdapter(Context context, List<Barang> barangs) {
        this.context = context;
        this.barangs = barangs;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgBarang;
        Button threedotsBtn;
        TextView tvBarangNama, tvBarangStok, tvKet1, tvKet2;
        public ViewHolder(@NonNull View view) {
            super(view);
            imgBarang=view.findViewById(R.id.iv_barang);
            tvBarangNama=view.findViewById(R.id.tv_barang_nama);
            tvBarangStok=view.findViewById(R.id.tv_barang_stok);
            tvKet1 = view.findViewById(R.id.tv_barang_ket1);
            tvKet2 = view.findViewById(R.id.tv_barang_ket2);
            threedotsBtn = view.findViewById(R.id.rv_threedots);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.list_barang,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        Barang barang = barangs.get(i);
        viewHolder.tvBarangNama.setText(barang.getNamaBarang());
        viewHolder.tvKet1.setText("Stock: ");
        viewHolder.tvBarangStok.setText(String.valueOf(barang.getStok()));
        viewHolder.tvKet2.setText(" Pcs");
        viewHolder.threedotsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                threedotsMenu(viewHolder, viewHolder.threedotsBtn);
            }
        });

        Glide.with(context)
                .load("http://192.168.1.86:8000/api/barang/image/"+barang.getImage())
                .into(viewHolder.imgBarang);
//        Toast.makeText(context, ""+barang.getImage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return barangs.size();
    }

    private void threedotsMenu(final ViewHolder viewHolder, Button threedotsBtn){
        PopupMenu popupMenu = new PopupMenu(context, threedotsBtn);
        popupMenu.inflate(R.menu.ic_threedots);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.threedots_ambil:
                        intentAmbilBarangActivity(viewHolder);
                        return true;
                    case R.id.threedots_edit:
                        intentEditBarangActivity(viewHolder);
                        return true;
                    case R.id.threedots_delete:
                        openDialog(viewHolder);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupMenu.show();
    }

    public void intentAmbilBarangActivity(ViewHolder viewHolder){
        int pos  = viewHolder.getAdapterPosition();
        Log.i("Cek", ""+pos);
        String intentNama = barangs.get(pos).getNamaBarang();
        String intentImage = barangs.get(pos).getImage();
        int intentStok =  barangs.get(pos).getStok();
        int intentId = barangs.get(pos).getId();
        Intent intent = new Intent(context, AmbilBarangActivity.class);

        Toast.makeText(context, ""+intentId, Toast.LENGTH_SHORT).show();
        intent.putExtra("idBarang", intentId);
        intent.putExtra("namaBarang", intentNama);
        intent.putExtra("stokBarang", intentStok);
        intent.putExtra("imageBarang", intentImage);
        context.startActivity(intent);
    }

    public void intentEditBarangActivity(ViewHolder viewHolder){
        int pos  = viewHolder.getAdapterPosition();
        Log.i("Cek", ""+pos);
        String intentNama = barangs.get(pos).getNamaBarang();
        Toast.makeText(context, ""+intentNama, Toast.LENGTH_SHORT).show();
        int intentId = barangs.get(pos).getId();
        int intentStok =  barangs.get(pos).getStok();
        String intentImage = barangs.get(pos).getImage();
        Intent intent = new Intent(context, EditBarangActivity.class);

        intent.putExtra("idBarang", intentId);
        intent.putExtra("namaBarang", intentNama);
        intent.putExtra("stokBarang", intentStok);
        intent.putExtra("imageBarang", intentImage);
        context.startActivity(intent);
    }

    public void deleteBarang(ViewHolder viewHolder){
        int pos  = viewHolder.getAdapterPosition();
        int id = barangs.get(pos).getId();
        ApiService service = ApiClient.getService(context);
        service.deleteBarang(id)
                .enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context, "Data Berhasil Dihapus",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(context, "Gagal Menyimpan.",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Toast.makeText(context, "Error:"+t, Toast.LENGTH_SHORT).show();
                    }
                });
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public void openDialog(final ViewHolder viewHolder){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Apakah anda yakin?")
                .setPositiveButton("Lanjutkan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteBarang(viewHolder);
                    }
                })
                .setNegativeButton("Batal", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

