package com.dovecry.graphdb.model;

import java.util.ArrayList;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.stereotype.Component;

@Component
@NodeEntity
public class ModelUser{
	@GraphId
	private Long userid;
	private String username;
	private String password;
	private String email;
	private String role;
	
	@Relationship(type="CREATES", direction=Relationship.UNDIRECTED)
	private ArrayList<ModelSong> songs = new ArrayList<ModelSong>();

	
	public ModelUser() {};
	
	public Long getUserid() {
		return userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email=email;
		
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setSongs(ArrayList<ModelSong> songs) {
		this.songs=songs;		
	}
	
	public ArrayList<ModelSong> getSongs(){
		return this.songs;
	}


}
