package com.example.natan.storagehoodieholic.api;

import com.example.natan.storagehoodieholic.model.Barang;
import com.example.natan.storagehoodieholic.model.Detection;
import com.example.natan.storagehoodieholic.model.ResponseModel;
import com.example.natan.storagehoodieholic.model.Transaksi;
import com.example.natan.storagehoodieholic.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    @FormUrlEncoded
    @POST("login")
    Call<User> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("signup")
    Call<ResponseModel> signup(@Field("name") String name,
                               @Field("username") String username,
                               @Field("password") String password);

    @GET("barang")
    Call<List<Barang>> getBarang();

    @GET("barang/transaksi")
    Call<List<Transaksi>> getTransaksi();

    @FormUrlEncoded
    @POST("barang/create")
    Call<ResponseModel> addBarang(@Field("nama_barang") String nama_barang, @Field("stok") int stok);

    @FormUrlEncoded
    @POST("barang/edit")
    Call<ResponseModel> editBarang(@Field("id") int id_barang,
                                   @Field("nama_barang") String nama_barang,
                                   @Field("stok") int stok);

    @FormUrlEncoded
    @POST("barang/delete")
    Call<ResponseModel> deleteBarang(@Field("id") int id_barang);

    @FormUrlEncoded
    @POST("barang/transaksi/create")
    Call<ResponseModel> ambilBarang(@Field("id") int id,
                                    @Field("jumlah") int jumlah);

    @FormUrlEncoded
    @POST("user/edit/{id}")
    Call<ResponseModel> editProfile(@Path("id") int id,
                                    @Field("name") String nama,
                                    @Field("username") String username);

    @Multipart
    @POST("barang/create")
    Call<ResponseModel> tambahBarang(@Part("nama_barang") RequestBody nama_barang,
                                     @Part("stok") RequestBody stok,
                                     @Part MultipartBody.Part image);

    @Multipart
    @POST("detection")
    Call<Detection> submitDetection(@Part("nama_barang") RequestBody nama_barang,
                                    @Part MultipartBody.Part image);

}
