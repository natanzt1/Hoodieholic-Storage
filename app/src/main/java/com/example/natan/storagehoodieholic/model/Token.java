package com.example.natan.storagehoodieholic.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Token{

	@SerializedName("expires_at")
	private String expiresAt;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private String id;

	@SerializedName("scopes")
	private List<Object> scopes;

	@SerializedName("revoked")
	private boolean revoked;

	@SerializedName("client_id")
	private int clientId;

	public void setExpiresAt(String expiresAt){
		this.expiresAt = expiresAt;
	}

	public String getExpiresAt(){
		return expiresAt;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
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

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setScopes(List<Object> scopes){
		this.scopes = scopes;
	}

	public List<Object> getScopes(){
		return scopes;
	}

	public void setRevoked(boolean revoked){
		this.revoked = revoked;
	}

	public boolean isRevoked(){
		return revoked;
	}

	public void setClientId(int clientId){
		this.clientId = clientId;
	}

	public int getClientId(){
		return clientId;
	}

	@Override
 	public String toString(){
		return 
			"Token{" + 
			"expires_at = '" + expiresAt + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",user_id = '" + userId + '\'' + 
			",name = '" + name + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",scopes = '" + scopes + '\'' + 
			",revoked = '" + revoked + '\'' + 
			",client_id = '" + clientId + '\'' + 
			"}";
		}
}