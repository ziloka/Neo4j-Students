package com.ziloka.neo4j.students;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.ziloka.neo4j.students.Student;

import java.util.List;

import org.springframework.data.neo4j.repository.query.Query;



interface StudentRepository extends Repository<Student, String> {

	@Query("MATCH (movie:Movie) WHERE movie.title CONTAINS $title RETURN movie")
	List<Student> findSearchResults(@Param("title") String title);
}