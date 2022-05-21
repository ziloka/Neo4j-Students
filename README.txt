= Students Example Application, students take courses

The source code is at
https://github.com/ziloka/Neo4j-Students/

This project demonstrates how to use Spring Boot, Spring Data, and Neo4j graphic database and D3 Javascript together.

This project is using Java 17,apache-maven-3.8.5

== Configuration options

|PORT
|8080

|NEO4J_URI
|bolt://localhost:7687

|NEO4J_USER
|neo4j

|NEO4J_PASSWORD
|password

|NEO4J_DATABASE
|students

|NEO4J_VERSION
|4.4
|===


*How to setup local development environment (Windows):
1. download and install OpenJDK from https://openjdk.java.net/

2. download and Install apache-maven-3.8.5 from https://maven.apache.org/download.cgi

3. download and Install git from https://git-scm.com/download/win
   use the command to clone this project
   git clone https://github.com/ziloka/Neo4j-Students/
   
4. download and install Eclipse IDE from https://www.eclipse.org/downloads/, "Get Eclipse IDE 2022-03"
   To import the existing maven project into Eclipse, choose the Neo4j-Students/pom.xml 
	
5. download and Install neo4j desktop, https://neo4j.com/download-neo4j-now/?utm_program=na-prospecting&utm_source=bing&utm_medium=cpc&utm_campaign=na-search-offers&utm_adgroup=desktop-download&utm_content=desktop-download&utm_program=&utm_source=bing&utm_medium=cpc&utm_campaign=&utm_adgroup=&msclkid=a3c8ca9f74771b993668a816744a2795
If you use other OS, please change #3 and 4 accordingly

6. Launch the neo4j desktop. After it is opened
   a. Please create a new project from directory, choose this project's folder Neo4j-Students.
   b. create a local database, name as students, password as password. You may change it to different password, and remember to update it in the application.properties on your local.
   c. start the student database
   c. populate data inside the students database. You should see a file called populate.cypher under src-> main-> resources from neo4j desktop. 
      Mouse moves over the file, click on the open button. It will be opened in the Neo4j Browser. Click on the blue arrow (run). 
	  Run the query MATCH (n) RETURN n; to double check.

7. How to build
	a. from command line, run command below under Neo4j-Students/, the project root where has pom.xml file.
	mvn clean package 
	b. build inside Eclipse IDE by selecting menu Project -> Build -> Clean

8. How to run the application
    a. from command line, run two commands below:
		cd target
		java -jar neo4j-students-0.0.1-SNAPSHOT.jar
	b. run inside Eclipse IDE by editing Run Configuration from Project menu. Add a Java application as StudentsApplication, select main class name called StudentsApplication. 
       At environment tab, add the followings parameters:   
        database.neo4j.dburi=bolt://localhost:7687
		database.neo4j.authentication.username=neo4j
		database.neo4j.authentication.password=password
		database.data.neo4j.database=students
		
	You should see something like below on the command prompt or Eclipse console log.
	"com.ziloka.neo4j.StudentsApplication     : Started StudentsApplication in 7.328 seconds (JVM running for 8.371)"
	There is a log file called studentApp.log in the target folder 
	
	c. You can debug the application through Eclipse IDE, just by running the Debug and set a breakpoint.
	
9. How to test
	load http://localhost:8080/ on a browser
10. Have fun!!

*References:
1. https://neo4j.com/use-cases/
2. https://neo4j.com/developer/example-data/
3. https://neo4j.com/developer/cypher/querying/
4. https://neo4j.com/developer-blog/visualizing-graphs-in-3d-with-webgl/
5. https://neo4j.com/developer/graph-visualization/