/*package com.dovecry.graphdb;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	private ISongRepository songRepository;
	
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
    	User adminUser = new User();
    	adminUser.setUsername("cshelton");
    	adminUser.setEmail("cshelton@email.com");
    	adminUser.setPassword("csheltonpw");
    	adminUser.setRole("ROLE_ADMIN");
    	Song song = new Song();
    	song.setSongname("Lonely Joe");
    	song.setSonglocation("/user/cshelton/songs/LonelyJoe");
    	song.setNeedsTracks(true);
    	Track t1 = new Track();
    	t1.setTrackname("Guitar");
    	t1.setTracklocation(song.getSonglocation()+"/"+t1.getTrackname());
    	t1.setTracktype(Track.TrackType.GUITAR);
    	ArrayList<Track> tracks =new ArrayList<Track>();
    	tracks.add(t1);
    	
    	song.setTracks(tracks);
    	ArrayList<Song> songs = new ArrayList<Song>();
    	songs.add(song);
    	adminUser.setSongs(songs);
    	session.save(adminUser);
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
    	User adminUser = new User();
    	adminUser.setUsername("cshelton");
    	adminUser.setEmail("cshelton@email.com");
    	adminUser.setPassword("csheltonpw");
    	adminUser.setRole("ROLE_ADMIN");
    	Song song = new Song();
    	song.setSongname("Lonely Joe");
    	song.setSonglocation("/user/cshelton/songs/LonelyJoe");
    	song.setNeedsTracks(true);
    	Track t1 = new Track();
    	t1.setTrackname("Guitar");
    	t1.setTracklocation(song.getSonglocation()+"/"+t1.getTrackname());
    	t1.setTracktype(Track.TrackType.GUITAR);
    	ArrayList<Track> tracks =new ArrayList<Track>();
    	tracks.add(t1);
    	song.setTracks(tracks);
    	ArrayList<Song> songs = new ArrayList<Song>();
    	songs.add(song);
    	adminUser.setSongs(songs);
    	session.save(adminUser);
    	SetupUsers setupUsers = new SetupUsers();
    	ArrayList<User> users = setupUsers.createUsers();
    	for(int i=0;i<users.size();i++) {
    		session.save(users.get(i));
    	}
    	session.getTransaction().commit();
    	String username = "username-2";
    	ArrayList<Song> songs1 = songRepository.getSongsByUserName(username);
    	System.out.println(songs1.get(0).getSongname().substring(0, username.length()));
    	assertEquals(username,(songs1.get(0).getSongname().substring(0, username.length())));
    	for(Song s: songs1) {
    		System.out.println(s.getSongname());
    	}
    	after();
    }
    
}
*/