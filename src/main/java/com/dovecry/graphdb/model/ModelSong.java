package com.dovecry.graphdb.model;

import java.util.ArrayList;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.stereotype.Component;

@Component
@NodeEntity
public class ModelSong {
	@GraphId
	private Long songid;
	private String songname;
	private String songlocation;
	private Boolean needstracks;
	
	@Relationship(type="CONTAINS", direction=Relationship.UNDIRECTED)
	private ArrayList<ModelTrack> tracks = new ArrayList<ModelTrack>();
	
	public ModelSong() {}

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

	public Boolean getNeedstracks() {
		return needstracks;
	}

	public void setNeedstracks(Boolean needstracks) {
		this.needstracks = needstracks;
	}



}
