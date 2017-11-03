package com.dovecry.spring;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.dovecry.graphdb")
@EnableNeo4jRepositories(basePackages="com.dovecry.graphdb.data")
@EnableTransactionManagement
//@PropertySource({"classpath:neo4j.properties"})
public class GraphDbConfiguration {
	@Bean
	public SessionFactory sessionFactory() {
		return new SessionFactory(configuration(),"com.dovecry.graphdb.model");
	}
	
	@Bean
	public Neo4jTransactionManager transactionManager() throws Exception{
		return new Neo4jTransactionManager(sessionFactory());
	}
	
	@Bean
	public org.neo4j.ogm.config.Configuration configuration(){
		org.neo4j.ogm.config.Configuration configuration = 
				new org.neo4j.ogm.config.Configuration();
		configuration.driverConfiguration()
		.setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
		.setURI("http://neo4j:needle@localhost:7474");
		return configuration;
	}
}
