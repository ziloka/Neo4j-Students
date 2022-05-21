package com.ziloka.neo4j.students;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.Transaction;
//import org.neo4j.driver.Value;
import org.neo4j.driver.exceptions.NoSuchRecordException;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.TypeSystem;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.neo4j.core.Neo4jClient;


import org.springframework.stereotype.Service;


import com.ziloka.neo4j.students.StudentsResultDto;

import lombok.extern.slf4j.Slf4j;

import com.ziloka.neo4j.properties.LoadDatabaseConfigurationBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


@Slf4j
@Service
public class StudentService implements AutoCloseable {

	private final Logger logger =  LoggerFactory.getLogger(StudentService.class);
	//private final StudentService studentService;
	
	 private static StudentService single_instance = null;

	
	 @Autowired
	 LoadDatabaseConfigurationBean loadDatabaseConfigurationBean;
	 
	private Neo4jClient neo4jClient;
	 	 
	private Driver driver;

	  @Override
	    public void close()
	    {
	        driver.close();
	    }
	  
	private String database() {
		return databaseSelectionProvider.getDatabaseSelection().getValue();
	}
	
    private Session session;
    
    public StudentService()
    {	
//    	String uri=  loadDatabaseConfigurationBean.getDatabaseURL();
//    	String user = loadDatabaseConfigurationBean.getDatabaseUserName();
//    
//    	String password = loadDatabaseConfigurationBean.getDatabasePassword();
//   	 
//    	
//    	logger.debug( "service username: " + user);
//    
//        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
//        session = driver.session();
        
    }
    
    
    public void setStudentService()
    {
    	
    	String uri=  loadDatabaseConfigurationBean.getDatabaseURL();
    	String user = loadDatabaseConfigurationBean.getDatabaseUserName();
    
    	String password = loadDatabaseConfigurationBean.getDatabasePassword();
   	 
    	
    	logger.debug( "service username: " + user);
    
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
        session = driver.session();
        
       
    }

    public StudentService( String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
        session = driver.session();
        
 
    }

    public static StudentService getInstance(String uri, String user, String password) {
        if (single_instance == null) single_instance = new StudentService(uri, user, password);
        return single_instance;
    }
	
	//  private static StudentRepository single_instance = null;

	 //   private final Session session;
	private DatabaseSelectionProvider databaseSelectionProvider;

	public void setStudentService(
				 Neo4jClient neo4jClient,
				 Driver driver,
				 DatabaseSelectionProvider databaseSelectionProvider) {

	//	this.studentRepository = studentRepository;
		this.neo4jClient = neo4jClient;
		this.driver = driver;
		this.databaseSelectionProvider = databaseSelectionProvider;
		
		
	}
	

		public StudentDetailsDto fetchDetailsByStudentName(String StudentName) {
			return this.neo4jClient
					.query("" +
							"MATCH (movie:Movie {title: $StudentName}) " +
							"OPTIONAL MATCH (person:Person)-[r]->(movie) " +
							"WITH movie, COLLECT({ name: person.name, job: REPLACE(TOLOWER(TYPE(r)), '_in', ''), role: HEAD(r.roles) }) as cast " +
							"RETURN movie { .title, cast: cast }"
					)
					.in(database())
					.bindAll(Map.of("title", StudentName))
					.fetchAs(StudentDetailsDto.class)
					//.mappedBy(this::toMovieDetails)
					.one()
					.orElse(null);
		}
		
		 public void populate() {
		        session.writeTransaction(tx -> {
		            Result result = null;
		            try {
		                InputStream in = getClass().getResourceAsStream("populate.cypher");
		                assert in != null;
		                Reader reader = new BufferedReader(new InputStreamReader(in, Charset.forName(StandardCharsets.UTF_8.name())));

		                StringBuilder textBuilder = new StringBuilder();
		                int c;
		                while ((c = reader.read()) != -1) {
		                    textBuilder.append((char) c);
		                }
		                result = tx.run(textBuilder.toString());
		            } catch(IOException e) {
		                e.printStackTrace();
		            }
		            return result;
		        });
		    }
		 
		 public List<StudentDetailsDto>  getStudentByName(String name) {
			 
			 StudentDetailsDto studentDetailsDto = null;
			if (session == null || ! session.isOpen() ) {
				//re-open
				setStudentService();
			}
			 try {
				 
				  logger.debug ("*********getStudentByName in");
				
			           
			           List<StudentDetailsDto> studentDetailsDtoList = new ArrayList<>();
			           
			           List<String> studentNames = new ArrayList<>();
			           String [] columns = {"first_name", "last_name"};
			         //  Result result = tx.run("MATCH (n:Student { last_name: $name }) RETURN n;", parameters("name", name));
			           List<HashMap> result = runQueryComplexResult("MATCH (student:Student { last_name: '"+ name +"' }) RETURN student;", "student", columns);
			           Iterator iteratorResult = result.iterator();
			           while (iteratorResult.hasNext()) {
			          // 
			        	HashMap studentMap = (HashMap)iteratorResult.next();
			        	
			        	logger.debug ("*********getStudentByName in first_name " +studentMap.get("first_name"));
			        	   studentDetailsDtoList.add(
			        			   new StudentDetailsDto( studentMap.get("first_name").toString()
			        					   ,studentMap.get("last_name").toString())
			        			   );
			        	   
			        	   logger.debug ("*********getStudentByName 2");
			           }
			           
			           if (studentDetailsDtoList.size()>0) {
			        	   return studentDetailsDtoList;
			           }
			           else 
			        	   return null;
			           			 
			 }
			catch (NoSuchRecordException e) {
					
			}
			 catch (Exception e1) {
				 e1.printStackTrace();
			 }
			 finally{
				 session.close();
			 }
			 
			 
			 return null;
		 }
		 
		
		 
		 /**
			 * This is an example of when you might want to use the pure driver in case you have no need for mapping at all, neither in the
			 * form of the way the {@link org.springframework.data.neo4j.core.Neo4jClient} allows and not in form of entities.
			 *
			 * @return A representation D3.js can handle
			 */
			public Map<String, List<Object>> fetchStudentGraph(String name) {

				var nodes = new ArrayList<>();
				var links = new ArrayList<>();
				if (session == null || ! session.isOpen() ) {
					//re-open
					logger.debug ("**session is null, reconnecting to DB.");
					setStudentService();
				}
				try (Session session = sessionFor()) {
				
					var records = session.readTransaction(tx -> tx.run(""
						+ "MATCH (student {last_name: '"+name+"'}) --(course) RETURN course, student"
					).list()
							);
					records.forEach(record -> {
						//var course = Map.of("description", "course", "name", record.get("course").asString());
						var last_name = record.get("student").get("last_name").asString();
						var first_name = record.get("student").get("first_name").asString();
						var student = Map.of("label","student", "title",  first_name + " " +last_name);
						
						//to build nodes,like
						//{
//					    "title": "Band",
//					      "label": "course"
//					    }
					//	var actor = Map.of("label", "actor", "title", name);
						
						//var course = Map.of("name", record.get("course").asObject().toString());
						
						//var targetIndex = nodes.size();
						var targetIndex = 0;
						if (! nodes.contains(student)) {
							nodes.add(student);
						}
						logger.debug("**getgraph, couse name=" +record.get("course").get("name").asString());
						//logger.debug("**record.get(\"course\")=" +record.get("course").get(0));
				
						var course = Map.of ("label","course", "title",record.get("course").get("name").asString());
						//var course = Map.of ("label","course", "name","**BAND");
							int sourceIndex;
							if (nodes.contains(course)) {
								sourceIndex = nodes.indexOf(course);
							} else {
								nodes.add(course);
								sourceIndex = nodes.size() - 1;
							}
							if (!links.contains(Map.of("source", sourceIndex, "target", targetIndex)))
								links.add(Map.of("source", sourceIndex, "target", targetIndex));
			
					});
					
					
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				finally {
					session.close();
				}
				return Map.of("nodes", nodes, "links", links);
			}
						
			private Session sessionFor() {
				return session;
//				if (database == null) {
//					return driver.session();
//				}
//				return driver.session(SessionConfig.forDatabase(database));
			}
			
			public List<HashMap> runQueryComplexResult(String query, String resultName, String...columns) {
				ArrayList<HashMap> all = new ArrayList<HashMap> ();
				
				List<Object> retvals = new ArrayList<Object>();
				logger.debug(query);
				
				try ( Transaction tx = session.beginTransaction()) {
					// log.info(query);
					Result result = tx.run(query);
					
					while(result.hasNext()) {
						Map<String,Object> row = result.next().asMap();
						
						//((Node) row.get("student")).get("first_name");
					   HashMap map = new HashMap();

						for(String col : columns) { 
							map.put(col, ((Node) row.get(resultName)).get(col));
							
//							if(!all.containsKey(col)) all.put(col, new ArrayList<Object>());
//							all.get(col).add(((Node) row.get(resultName)).get(col));
						}
						all.add(map);
					}	
					
					tx.close();
				} finally {
					session.close();
				}
				
				return all;
			}
			
			public Map<String,List<Object>> runQueryComplexResult2(String query, String resultName, String...columns) {
				HashMap<String,List<Object>> all = new HashMap<String,List<Object>>();
				
				List<Object> retvals = new ArrayList<Object>();
				System.out.println(query);
				
				try ( Transaction tx = session.beginTransaction()) {
					// log.info(query);
					Result result = tx.run(query);
					
					while(result.hasNext()) {
						Map<String,Object> row = result.next().asMap();
						
						//((Node) row.get("student")).get("first_name");
					   

						for(String col : columns) { 
							if(!all.containsKey(col)) all.put(col, new ArrayList<Object>());
							all.get(col).add(((Node) row.get(resultName)).get(col));
						}
					}	
					
					tx.close();
				} finally {
					session.close();
				}
				
				return all;
			}
}
