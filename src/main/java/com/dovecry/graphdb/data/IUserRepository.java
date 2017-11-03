package com.dovecry.graphdb.data;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.dovecry.graphdb.model.User;

public interface IUserRepository extends GraphRepository<User>{
	@Query("MATCH (n:User) RETURN n.username")
	public List<String> getAllUserNames();

	public User findByUsername(String username);
}
