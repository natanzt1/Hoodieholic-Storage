package com.example.natan.storagehoodieholic.model;

public class ResponseModel {
    private String image;
    private String hasil;

    public ResponseModel(String image, String hasil) {
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
}
