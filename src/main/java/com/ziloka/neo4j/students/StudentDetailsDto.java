package com.ziloka.neo4j.students;

import java.util.Objects;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;


public class StudentDetailsDto {

    private final String firstName;
    private final String lastName;

    public StudentDetailsDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDetailsDto that = (StudentDetailsDto) o;
        return Objects.equals(firstName, that.firstName) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName);
    }
}
