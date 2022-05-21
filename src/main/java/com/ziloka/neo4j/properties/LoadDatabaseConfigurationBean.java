package com.ziloka.neo4j.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.ziloka.neo4j.students.StudentService;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@ConfigurationProperties(prefix="database.neo4j")
@Configuration
@Slf4j
public class LoadDatabaseConfigurationBean {
	private final Logger logger =  LoggerFactory.getLogger(LoadDatabaseConfigurationBean.class);
	 @Value("${database.neo4j.authentication.username}")
	  private String userName;


	  @Value("${database.neo4j.authentication.password}")
	  private String password;
	  
	  @Value("${database.neo4j.dburi:bolt://localhost:7687}")
	   private String dbURL;
	 
	  public String getDatabaseURL () {
		 
		  logger.debug ("url: "+dbURL);
	    	return dbURL;
	    }
	 
	    public String getDatabasePassword () {
	    	//password = "password";
	    	logger.debug("password: "+ password);
	    	return password;
	    }
	    
	    public String getDatabaseUserName () {
	    	//password = "password";
	    	logger.debug ("userName: "+ userName);
	    	return userName;
	    }
	
}



