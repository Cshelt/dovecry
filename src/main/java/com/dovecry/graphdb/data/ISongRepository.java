package com.dovecry.graphdb.data;

import java.util.ArrayList;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.dovecry.graphdb.model.ModelSong;

public interface ISongRepository extends GraphRepository<ModelSong>{
	@Query("MATCH (u:ModelUser)-[:CREATED]->(s:ModelSong) WHERE u.username={0} return s")
	public ArrayList<ModelSong> getSongsByUserName(String username);
	
}