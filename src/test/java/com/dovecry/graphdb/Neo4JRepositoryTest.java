package com.dovecry.graphdb;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.dovecry.graphdb.data.ISongRepository;
import com.dovecry.graphdb.model.Song;
import com.dovecry.graphdb.model.Track;
import com.dovecry.graphdb.model.User;
import com.dovecry.spring.GraphDbConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { GraphDbConfiguration.class }, loader = AnnotationConfigContextLoader.class)
public class Neo4JRepositoryTest {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private Neo4jTransactionManager transactionManager;
	
	@Autowired
	private ISongRepository songRepository;
	
	private Transaction transaction;
	
	private Session session;
	
	@Before
    public final void before() {
        session = sessionFactory.openSession();
    }

    @After
    public final void after() {
    	session.clear();
    }
    
    @Test
    public void testAddingUser() {
    	session.beginTransaction();
    	session.deleteAll(User.class);
    	session.deleteAll(Song.class);
    	session.deleteAll(Track.class);
    	SetupUsers setupUsers = new SetupUsers();
    	ArrayList<User> users = setupUsers.createUsers();
    	for(int i=0;i<users.size();i++) {
    		session.save(users.get(i));
    	}
    	session.getTransaction().commit();
    	after();
    }
    
    @Test
    public void testGetUsersSongs() {
    	session.beginTransaction();
    	session.deleteAll(User.class);
    	session.deleteAll(Song.class);
    	session.deleteAll(Track.class);
    	SetupUsers setupUsers = new SetupUsers();
    	ArrayList<User> users = setupUsers.createUsers();
    	for(int i=0;i<users.size();i++) {
    		session.save(users.get(i));
    	}
    	session.getTransaction().commit();
    	String username = "username-2";
    	ArrayList<Song> songs = songRepository.getSongsByUserName(username);
    	System.out.println(songs.get(0).getSongname().substring(0, username.length()));
    	assertEquals(username,(songs.get(0).getSongname().substring(0, username.length())));
    	for(Song s: songs) {
    		System.out.println(s.getSongname());
    	}
    	after();
    }
    
}
