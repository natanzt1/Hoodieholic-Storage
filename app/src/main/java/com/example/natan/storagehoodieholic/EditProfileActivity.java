package com.example.natan.storagehoodieholic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.natan.storagehoodieholic.api.ApiClient;
import com.example.natan.storagehoodieholic.api.ApiService;
import com.example.natan.storagehoodieholic.helper.preferenceHelper;
import com.example.natan.storagehoodieholic.model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    EditText etEditNama, etEditUsername;
    ImageView ivEditGambar;
    Button editSubmitBtn;

//    Program mulai berjalan dari sini
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setEditText();
        editSubmitBtn = findViewById(R.id.edit_submit_button);

        editSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanPerubahan();
            }
        });
    }

//    Koding ini berfungsi untuk mengisi gambar, nama dan username awal dari user
    public void setEditText(){
        etEditNama = findViewById(R.id.et_edit_nama);
        etEditUsername = findViewById(R.id.et_edit_username);

        preferenceHelper preference = new preferenceHelper(this);
        String profileNama = preference.getPreferenceNama();
        String profileUsername = preference.getPreferenceUsername();

        etEditNama.setText(profileNama);
        etEditUsername.setText(profileUsername);
    }

//    Koding ini berfungsi untuk menyimpan perubahan data ke dalam server.
    public void simpanPerubahan(){
//        Koding getText untuk mengambil data dari form input nama dan username
        String nama = etEditNama.getText().toString();
        String username = etEditUsername.getText().toString();
        preferenceHelper preference = new preferenceHelper(this);
        int id = preference.getPreferenceId();

//      Kodingan CallApi() dijalankan untuk menimpan perubahan data ke dalam server
        callApi(nama, username, id);
        preference.setName(nama);
        preference.setUsername(username);
    }

//  Koding ini yang dijalankan untuk menyimpan data perubahan ke server.
    private void callApi(String nama, String username, int id) {
        ApiService service = ApiClient.getService(this);
        service.editProfile(id, nama, username)
                .enqueue(new Callback<ResponseModel>(){
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(EditProfileActivity.this, "Data Suskses tersimpan",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(EditProfileActivity.this, "Gagal Menyimpan. "+response.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Toast.makeText(EditProfileActivity.this, "Error:"+t, Toast.LENGTH_SHORT).show();
                    }
                });
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
