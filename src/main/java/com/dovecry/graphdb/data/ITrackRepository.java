package com.dovecry.graphdb.data;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.dovecry.graphdb.model.ModelTrack;

public interface ITrackRepository extends GraphRepository<ModelTrack>{

}
