package com.example.natan.storagehoodieholic.model;

import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("name")
    private String name;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private int id;

    @SerializedName("username")
    private String username;

    @SerializedName("token")
    private String token;

    public void setUpdatedAt(String updatedAt){
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt(){
        return updatedAt;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }

    @Override
    public String toString(){
        return
                "User{" +
                        "updated_at = '" + updatedAt + '\'' +
                        ",name = '" + name + '\'' +
                        ",created_at = '" + createdAt + '\'' +
                        ",id = '" + id + '\'' +
                        ",username = '" + username + '\'' +
                        ",token = '" + token + '\'' +
                        "}";
    }

    public static class Entry implements BaseColumns {
        public static final String TABLE_NAME="tb_users";
        public static final String COLUMN_ID="id";
        public static final String COLUMN_NAME="name";
        public static final String COLUMN_USERNAME="username";
        public static final String COLUMN_UPDATED="updated_at";
    }
}
