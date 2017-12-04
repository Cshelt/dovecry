package com.dovecry.graphdb.model;

import java.util.ArrayList;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class ModelSong {
	@GraphId
	private Long songid;
	private String songname;
	private String songlocation;
	private Boolean needsTracks;
	
	@Relationship(type="CONTAINS", direction=Relationship.UNDIRECTED)
	private ArrayList<ModelTrack> tracks = new ArrayList<ModelTrack>();
	
	public ModelSong() {};

	public Long getSongid() {
		return songid;
	}

	public String getSongname() {
		return songname;
	}

	public void setSongname(String songname) {
		this.songname = songname;
	}

	public String getSonglocation() {
		return songlocation;
	}

	public void setSonglocation(String songlocation) {
		this.songlocation = songlocation;
	}

	public ArrayList<ModelTrack> getTracks() {
		return tracks;
	}

	public void setTracks(ArrayList<ModelTrack> tracks) {
		this.tracks = tracks;
	}

	public Boolean getNeedsTracks() {
		return needsTracks;
	}

	public void setNeedsTracks(Boolean needsTracks) {
		this.needsTracks = needsTracks;
	}
}
