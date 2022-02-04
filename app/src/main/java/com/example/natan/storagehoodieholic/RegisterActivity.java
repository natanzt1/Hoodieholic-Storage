package com.example.natan.storagehoodieholic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.natan.storagehoodieholic.api.ApiClient;
import com.example.natan.storagehoodieholic.api.ApiService;
import com.example.natan.storagehoodieholic.model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText etNama, etUsername, etPassword;
    Button submitBtn;

    //    Program mulai berjalan dari sini
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        submitBtn = findViewById(R.id.register_submit_button);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanPerubahan();
            }
        });
    }

    //    Koding ini berfungsi untuk menyimpan perubahan data ke dalam server.
    public void simpanPerubahan(){
        etNama = findViewById(R.id.et_register_nama);
        etUsername = findViewById(R.id.et_register_username);
        etPassword = findViewById(R.id.et_register_password);
//        Koding getText untuk mengambil data dari form input nama, username, dan password
        String nama = etNama.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

//      Kodingan CallApi() dijalankan untuk menyimpan perubahan data ke dalam server
        callApi(nama, username, password);
    }

    //  Koding ini yang dijalankan untuk menyimpan data perubahan ke server.
    private void callApi(String name, String username, String password) {
        ApiService service = ApiClient.getService(this);
        service.signup(name, username, password)
                .enqueue(new Callback<ResponseModel>(){
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Registrasi Berhasil",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, Activity_login.class);
                            startActivity(intent);
                            RegisterActivity.this.finish();
                        }else {
                            Toast.makeText(RegisterActivity.this, "Gagal Menyimpan. "+response.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Error:"+t, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
