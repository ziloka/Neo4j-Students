package com.ziloka.neo4j.students;

import java.util.Objects;

public class StudentsResultDto {
	
    private final StudentDetailsDto studentDetailsDto;

    public StudentsResultDto (StudentDetailsDto studentDetails) {
        this.studentDetailsDto = studentDetails;
    }

    public StudentDetailsDto getStudentDetails() {
        return studentDetailsDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentsResultDto that = (StudentsResultDto) o;
        return Objects.equals(studentDetailsDto, that.studentDetailsDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentDetailsDto);
    }

}
