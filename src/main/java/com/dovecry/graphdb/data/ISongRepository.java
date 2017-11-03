package com.dovecry.graphdb.data;

import java.util.ArrayList;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.dovecry.graphdb.model.Song;
import com.dovecry.graphdb.model.User;

public interface ISongRepository extends GraphRepository<Song>{
	@Query("MATCH (u:User)-[:CREATED]->(s:Song) WHERE u.username={0} return s")
	public ArrayList<Song> getSongsByUserName(String username);
	
}