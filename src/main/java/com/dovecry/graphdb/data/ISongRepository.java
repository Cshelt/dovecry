package com.dovecry.graphdb.data;

import java.util.ArrayList;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.dovecry.graphdb.model.ModelSong;

public interface ISongRepository extends GraphRepository<ModelSong>{
	@Query("MATCH (u:ModelUser)-[:CREATED]->(s:ModelSong) WHERE u.username={0} return s")
	public ArrayList<ModelSong> getSongsByUserName(String username);
	
	@Query("MATCH (n:ModelUser) WHERE n.username={0} CREATE (s:ModelSong{songname:{1},songlocation:{2},needstracks:{3}})<-[:CREATES]-(n)")
	public void createSongByUsername(String username,String songname,String songlocation,Boolean needstracks);
	
}