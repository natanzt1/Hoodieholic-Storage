package com.example.natan.storagehoodieholic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.natan.storagehoodieholic.api.ApiClient;
import com.example.natan.storagehoodieholic.api.ApiService;
import com.example.natan.storagehoodieholic.helper.preferenceHelper;
import com.example.natan.storagehoodieholic.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_login extends AppCompatActivity {
    SharedPreferences userPreference;
    Button button, signupBtn;
    ApiService service;
    RelativeLayout rellay1, rellay2;
    EditText et_username, et_password;
    TextView tv_username, tv_password;
    String username, password;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
        rellay1.setVisibility(View.VISIBLE);
        rellay2.setVisibility(View.VISIBLE);
        }
    };

//    Program dalam Activity selalu dimulai dari protected void onCreate(){
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userPreference = this.getSharedPreferences("User_Preferance", Context.MODE_PRIVATE);

//        Sistem mengecek apakah user sudah login atau belum dengan melihat apakah ada token dalam sharedprefrences
//        User yang sudah login akan diarahkan ke MainActivity
        String token = userPreference.getString("token", "missing");
        if (token != "missing") {
            openMainActivity();
        }
//

        rellay1= (RelativeLayout) findViewById(R.id.rellay1);
        rellay2= (RelativeLayout) findViewById(R.id.rellay2);
        tv_username = findViewById(R.id.tv_username);
        tv_password = findViewById(R.id.tv_password);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        handler.postDelayed(runnable, 2000);

        button = (Button) findViewById(R.id.button);
        signupBtn = findViewById(R.id.signup_btn);
//        Ketika tombol button ditekan maka sistem akan menjalankan fungsi login() yg ada di bawah ini
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               login();
            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_login.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

//    Sistem mengambil username dan password inputan user
    public void login() {
        username = et_username.getText().toString();
        password = et_password.getText().toString();
        if(validate(username, password)){
            service = ApiClient.getService(this);
//            Sistem mengepush data username dan password ke server untuk login
            service.login(username, password).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
//                    bila user berhasil login, maka sistem akan menyimpan identitas user dalam sharedpreference
//                    Kode utk menyimpan data dalam sharedpreference dapat dilihat pada setpreferances();
                    if (response.isSuccessful()){
                        setPreferances(response.body());
                        openMainActivity();
                    }else {
                        Toast.makeText(Activity_login.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(Activity_login.this, "Failed :"+t, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(Activity_login.this, "Failed Login", Toast.LENGTH_SHORT).show();
        };
    }

    public void setPreferances(User user){
        preferenceHelper preferenceHelper = new preferenceHelper(this);
//        Fungsi untuk menyimpan data dalam sharedpreferences dapat dilihat pada folder Helper->preferencesHelper->setLogin()
        preferenceHelper.setLogin(user);
    }

    public boolean validate(String username, String password){
        if(username.equals("")){
            et_username.setError("Silahkan isi field ini!");
            return false;
        }

        if(password.equals("")){
            et_password.setError("Silahkan isi field ini!");
            return false;
        }

        return true;
    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
