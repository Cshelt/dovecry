package com.dovecry.graphdb.data;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.dovecry.graphdb.model.Track;

public interface ITrackRepository extends GraphRepository<Track>{

}
