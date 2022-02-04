package com.example.natan.storagehoodieholic.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.natan.storagehoodieholic.model.User;

public class preferenceHelper {
    private SharedPreferences sharedPreferences;
    private String name, username, token;
    private int id;

    public preferenceHelper(Context context){
        sharedPreferences = context.getSharedPreferences("User_Preferance", context.MODE_PRIVATE);
    }

    public int getPreferenceId(){
        id = sharedPreferences.getInt("id", 0);
        return id;
    }

    public String getPreferenceNama(){
        name = sharedPreferences.getString("name", "");
        return name;
    }

    public String getPreferenceUsername(){
        username = sharedPreferences.getString("username", "");
        return username;
    }

    public String getPreferenceToken(){
        token = sharedPreferences.getString("token", "");
        return token;
    }

    public void setLogin(User user){
        setId(user.getId());
        setName(user.getName());
        setUsername(user.getUsername());
        setToken(user.getToken());
    }

    public void setId(int id){
        sharedPreferences.edit().putInt("id",id).apply();
    }

    public void setName(String name){
        sharedPreferences.edit().putString("name",name).apply();
    }

    public void setUsername(String username){
        sharedPreferences.edit().putString("username", username).apply();
    }

    public void setToken(String token){
        sharedPreferences.edit().putString("token", token).apply();
    }

    public void setLogout(){
        sharedPreferences.edit()
                .clear()
                .apply();
    }

    public String getToken() {
        return sharedPreferences.getString("token", "");
    }
}
