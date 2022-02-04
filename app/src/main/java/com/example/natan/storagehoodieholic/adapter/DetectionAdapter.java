package com.example.natan.storagehoodieholic.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.natan.storagehoodieholic.R;
import com.example.natan.storagehoodieholic.model.Detection;

import java.util.List;

public class DetectionAdapter extends RecyclerView.Adapter<DetectionAdapter.ViewHolder>{
    private Context context;
    private Detection detections;
    private static final String TAG = "TAG RECYCLER";

    public DetectionAdapter(Context context, Detection detections) {
        this.context = context;
        this.detections = detections;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgDetection;
        Button threedotsBtn;
        TextView tvBarangNama, tvBarangStok, tvKet1, tvKet2;
        public ViewHolder(@NonNull View view) {
            super(view);
            imgDetection=view.findViewById(R.id.iv_barang);
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
        Detection detection = detections;
        viewHolder.tvBarangNama.setText(detection.getHasil());
        viewHolder.tvKet1.setText("Stock: ");
//        viewHolder.threedotsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                threedotsMenu(viewHolder, viewHolder.threedotsBtn);
//            }
//        });

        Glide.with(context)
                .load("http://192.168.1.86:8000/api/barang/image/"+detection.getImage())
                .into(viewHolder.imgDetection);
//        Toast.makeText(context, ""+barang.getImage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return 1;
    }

}

