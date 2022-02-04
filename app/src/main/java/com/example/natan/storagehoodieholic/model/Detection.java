package com.example.natan.storagehoodieholic.model;

import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

public class Detection {
    @SerializedName("image")
    private String image;
    @SerializedName("hasil")
    private String hasil;

    public Detection(String image, String hasil) {
        this.image = image;
        this.hasil = hasil;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getImage(){
        return image;
    }

    public void setHasil(String hasil){
        this.hasil = hasil;
    }

    public String getHasil(){
        return hasil;
    }
//
    public static class Entry implements BaseColumns {
        public static final String COLUMN_IMAGE="image";
        public static final String COLUMN_HASIL="hasil";
    }

    @Override
    public String toString(){
        return
                "Test{" +
                        "image = '" + image + '\'' +
                        "hasil = '" + hasil + '\'' +
                        "}";
    }
}
