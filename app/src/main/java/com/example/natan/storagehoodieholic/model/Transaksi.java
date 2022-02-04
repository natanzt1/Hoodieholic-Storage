package com.example.natan.storagehoodieholic.model;

import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;


public class Transaksi{
	public Transaksi(int jumlah, String updatedAt, int idBarang, int userId, String namaBarang, int id, String namaUser, String gambar) {
		this.jumlah = jumlah;
		this.updatedAt = updatedAt;
		this.idBarang = idBarang;
		this.userId = userId;
		this.namaBarang = namaBarang;
		this.id = id;
		this.namaUser = namaUser;
		this.gambar = gambar;
	}

	@SerializedName("jumlah")
	private int jumlah;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("id_barang")
	private int idBarang;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("id")
	private int id;

	@SerializedName("nama_user")
	private String namaUser;

	@SerializedName("gambar")
	private String gambar;

	public void setJumlah(int jumlah){
		this.jumlah = jumlah;
	}

	public int getJumlah(){
		return jumlah;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setIdBarang(int idBarang){
		this.idBarang = idBarang;
	}

	public int getIdBarang(){
		return idBarang;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
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

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setNamaUser(String namaUser){
		this.namaUser = namaUser;
	}

	public String getNamaUser(){
		return namaUser;
	}

	public void setGambar(String gambar){
		this.gambar = gambar;
	}

	public String getGambar(){
		return gambar;
	}

	@Override
	public String toString(){
		return
				"Tes{" +
						"jumlah = '" + jumlah + '\'' +
						",updated_at = '" + updatedAt + '\'' +
						",id_barang = '" + idBarang + '\'' +
						",user_id = '" + userId + '\'' +
						",created_at = '" + createdAt + '\'' +
						",nama_barang = '" + namaBarang + '\'' +
						",id = '" + id + '\'' +
						",nama_user = '" + namaUser + '\'' +
						",gambar = '" + gambar + '\'' +
						"}";
	}

	public static class Entry implements BaseColumns {
		public static final String TABLE_NAME="tb_transaksi";
		public static final String COLUMN_ID="id";
		public static final String COLUMN_ID_BARANG="id_barang";
		public static final String COLUMN_JUMLAH="jumlah";
		public static final String COLUMN_USER_ID="user_id";
		public static final String COLUMN_NAMA_USER="nama_user";
		public static final String COLUMN_NAMA_BARANG="nama_barang";
		public static final String COLUMN_GAMBAR ="image";
		public static final String COLUMN_CREATED="created_at";
		public static final String COLUMN_UPDATED="updated_at";
	}
}