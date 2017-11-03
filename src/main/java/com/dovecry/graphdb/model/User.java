package com.dovecry.graphdb.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.stereotype.Component;

@Component
@NodeEntity
public class User implements Serializable{
	private static final long serialVersionUID = 5026351515172046778L;
	@GraphId
	private Long userid;
	private String username;
	private String email;
	private String password;
	
	@Relationship(type="CREATED",direction=Relationship.UNDIRECTED)
	private ArrayList<Song> songs = new ArrayList<Song>();
	
	public User() {};
	
	public Long getUserid() {
		return userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Song> getSongs() {
		return songs;
	}

	public void setSongs(ArrayList<Song> songs) {
		this.songs = songs;
	}

}
