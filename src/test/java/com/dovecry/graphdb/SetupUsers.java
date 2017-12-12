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

import com.dovecry.graphdb.model.ModelSong;
import com.dovecry.graphdb.model.ModelTrack;
import com.dovecry.graphdb.model.ModelTrack.TrackType;
import com.dovecry.graphdb.model.ModelUser;
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
    	session.deleteAll(ModelUser.class);
    	session.deleteAll(ModelSong.class);
    	session.deleteAll(ModelTrack.class);
    	ModelUser adminUser = new ModelUser();
    	adminUser.setUsername("cshelton");
    	adminUser.setEmail("cshelton@email.com");
    	adminUser.setPassword("csheltonpw");
    	adminUser.setRole("ADMIN");
    	session.save(adminUser);
    	ArrayList<ModelUser> users;
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
    
    public ArrayList<ModelUser> createUsers() {
    	ArrayList<ModelUser> users = new ArrayList<ModelUser>();
    	for(int i=0;i<20;i++) {
    		ModelUser user = new ModelUser();
    		user.setUsername("username-".concat(String.valueOf(i)));
    		user.setEmail("username-".concat(String.valueOf(i)).concat("@email.com"));
    		user.setPassword("username-".concat(String.valueOf(i)).concat("-password"));
    		user.setRole("username-".concat(String.valueOf(i)));
    		ArrayList<ModelSong> songs = new ArrayList<ModelSong>();
    		for(int j=0;j<5;j++) {
    			ModelSong song = new ModelSong();
    			song.setSongname(user.getUsername()+"_SongName"+j);
    			song.setSonglocation("/user/song/users/"+user.getUsername()+"/"+song.getSongid());
    			song.setNeedstracks(j%2==0?true:false);
        		songs.add(song);
    		}
    		user.setSongs(songs);
    		users.add(user);
    	}
    	return users;
    }

	private ArrayList<ModelTrack> setUpTracks(int j, ModelSong song) {
		ArrayList<ModelTrack> tracks = new ArrayList<ModelTrack>();
		for (int k=0;k<10;k++) {
			ModelTrack track = new ModelTrack();
			track.setTrackname("TrackName_"+trackTypeFunction(k));;
			track.setTracklocation(song.getSonglocation()+"/"+track.getTrackname());
			ModelTrack.TrackType trackType = trackTypeFunction(k);
			track.setTracktype(trackType);
			tracks.add(track);
		}
		return tracks;	
	}

	private ModelTrack.TrackType trackTypeFunction(int k) {
		TrackType trackType = null;
		switch (k) {
		case 1: trackType=ModelTrack.TrackType.VOCAL;
			break;
		case 2: trackType=ModelTrack.TrackType.GUITAR;
		break;
		case 3: trackType=ModelTrack.TrackType.BASS;
		break;
		case 4: trackType=ModelTrack.TrackType.DRUM;
		break;
		case 5: trackType=ModelTrack.TrackType.OTHER;
		break;
		case 6: trackType=ModelTrack.TrackType.OTHER;
		break;
		case 7: trackType=ModelTrack.TrackType.GUITAR;
		break;
		case 8: trackType=ModelTrack.TrackType.BASS;
		break;
		case 9: trackType=ModelTrack.TrackType.VOCAL;
		break;
		case 10: trackType=ModelTrack.TrackType.VOCAL;
		break;
		default: trackType=ModelTrack.TrackType.OTHER;
		}	
	
		return trackType;
	}
}