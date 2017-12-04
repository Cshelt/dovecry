package com.dovecry.graphdb.data;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.dovecry.graphdb.model.ModelUser;

public interface IUserRepository extends GraphRepository<ModelUser>{
	@Query("MATCH (n:ModelUser) RETURN n.username")
	public List<String> getAllUserNames();

	public ModelUser findByUsername(String username);
}
