package com.dovecry.graphdb.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class ModelTrack {
	@GraphId
	private Long trackid;
	private String trackname;
	private String tracklocation;
	private TrackType tracktype;

	public Long getTrackid() {
		return trackid;
	}

	public String getTrackname() {
		return trackname;
	}

	public void setTrackname(String trackname) {
		this.trackname = trackname;
	}

	public String getTracklocation() {
		return tracklocation;
	}

	public void setTracklocation(String tracklocation) {
		this.tracklocation = tracklocation;
	}

	public TrackType getTracktype() {
		return tracktype;
	}

	public void setTracktype(TrackType tracktype) {
		this.tracktype = tracktype;
	}
	
	public enum TrackType{
		VOCAL,GUITAR,BASS,DRUM,OTHER
	}
	
}
