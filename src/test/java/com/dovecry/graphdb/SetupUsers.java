package com.dovecry.graphdb;

import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.dovecry.graphdb.model.Song;
import com.dovecry.graphdb.model.Track;
import com.dovecry.graphdb.model.Track.TrackType;
import com.dovecry.graphdb.model.User;
import com.dovecry.spring.GraphDbConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { GraphDbConfiguration.class }, loader = AnnotationConfigContextLoader.class)
public class SetupUsers {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private Neo4jTransactionManager transactionManager;
	
	private Transaction transaction;
	
	private Session session;

	@Test
	public void setUpDbWithUsers() {
		before();
		session.beginTransaction();
    	session.deleteAll(User.class);
    	session.deleteAll(Song.class);
    	session.deleteAll(Track.class);
    	ArrayList<User> users;
    	users=this.createUsers();
    	for(int i=0;i<users.size();i++) {
    		session.save(users.get(i));
    	}
    	session.getTransaction().commit();
    	after();
	}
	
    public final void before() {
        session = sessionFactory.openSession();
    }

    public final void after() {
    	session.clear();
    }
    
    public ArrayList<User> createUsers() {
    	ArrayList<User> users = new ArrayList<User>();
    	for(int i=0;i<50;i++) {
    		User user = new User();
    		user.setUsername("username-".concat(String.valueOf(i)));
    		user.setEmail("username-".concat(String.valueOf(i)).concat("@email.com"));
    		user.setPassword("username-".concat(String.valueOf(i)).concat("-password"));
    		ArrayList<Song> songs = new ArrayList<Song>();
    		for(int j=0;j<5;j++) {
    			Song song = new Song();
    			song.setSongname(user.getUsername()+"_SongName"+j);
    			song.setSonglocation("/user/song/users/"+user.getUsername()+"/"+song.getSongname());
    			song.setNeedsTracks(j%2==0?true:false);
    			song.setTracks(setUpTracks(j,song));
        		songs.add(song);
    		}
    		user.setSongs(songs);
    		users.add(user);
    	}
    	return users;
    }

	private ArrayList<Track> setUpTracks(int j, Song song) {
		ArrayList<Track> tracks = new ArrayList<Track>();
		for (int k=0;k<10;k++) {
			Track track = new Track();
			track.setTrackname("TrackName_"+trackTypeFunction(k));;
			track.setTracklocation(song.getSonglocation()+"/"+track.getTrackname());
			Track.TrackType trackType = trackTypeFunction(k);
			track.setTracktype(trackType);
			tracks.add(track);
		}
		return tracks;	
	}

	private Track.TrackType trackTypeFunction(int k) {
		TrackType trackType = null;
		switch (k) {
		case 1: trackType=Track.TrackType.VOCAL;
			break;
		case 2: trackType=Track.TrackType.GUITAR;
		break;
		case 3: trackType=Track.TrackType.BASS;
		break;
		case 4: trackType=Track.TrackType.DRUM;
		break;
		case 5: trackType=Track.TrackType.OTHER;
		break;
		case 6: trackType=Track.TrackType.OTHER;
		break;
		case 7: trackType=Track.TrackType.GUITAR;
		break;
		case 8: trackType=Track.TrackType.BASS;
		break;
		case 9: trackType=Track.TrackType.VOCAL;
		break;
		case 10: trackType=Track.TrackType.VOCAL;
		break;
		default: trackType=Track.TrackType.OTHER;
		}	
	
		return trackType;
	}
}
