package com.example.natan.storagehoodieholic.model;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin{

	@SerializedName("accessToken")
	private String accessToken;

	@SerializedName("token")
	private Token token;

	public void setAccessToken(String accessToken){
		this.accessToken = accessToken;
	}

	public String getAccessToken(){
		return accessToken;
	}

	public void setToken(Token token){
		this.token = token;
	}

	public Token getToken(){
		return token;
	}

	@Override
 	public String toString(){
		return 
			"ResponseLogin{" + 
			"accessToken = '" + accessToken + '\'' + 
			",token = '" + token + '\'' + 
			"}";
		}
}