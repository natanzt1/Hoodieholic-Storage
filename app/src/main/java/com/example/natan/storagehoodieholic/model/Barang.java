package com.example.natan.storagehoodieholic.model;

import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

public class Barang {
    @SerializedName("image")
    private String image;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("id")
    private int id;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("nama_barang")
    private String namaBarang;

    @SerializedName("stok")
    private int stok;

    public Barang(int id, String nama, int stok, String image) {
        this.id = id;
        this.namaBarang = nama;
        this.stok = stok;
        this.image = image;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){
        return image;
    }

    public void setUpdatedAt(String updatedAt){
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt(){
        return updatedAt;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public void setNamaBarang(String namaBarang){
        this.namaBarang = namaBarang;
    }

    public String getNamaBarang(){
        return namaBarang;
    }

    public void setStok(int stok){
        this.stok = stok;
    }

    public int getStok(){
        return stok;
    }

    @Override
    public String toString(){
        return
                "Test{" +
                        "image = '" + image + '\'' +
                        ",updated_at = '" + updatedAt + '\'' +
                        ",id = '" + id + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",nama_barang = '" + namaBarang + '\'' +
                        ",stok = '" + stok + '\'' +
                        "}";
    }

    public static class Entry implements BaseColumns {
        public static final String TABLE_NAME="tb_barang";
        public static final String COLUMN_ID="id";
        public static final String COLUMN_NAMA_BARANG="nama_barang";
        public static final String COLUMN_STOK="stok";
        public static final String COLUMN_IMAGE="image";
        public static final String COLUMN_UPDATED="updated_at";
    }
}
