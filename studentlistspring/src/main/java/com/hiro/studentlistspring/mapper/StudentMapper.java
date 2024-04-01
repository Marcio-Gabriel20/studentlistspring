package com.hiro.studentlistspring.mapper;

import com.hiro.studentlistspring.dto.StudentDTO;
import com.hiro.studentlistspring.model.Student;

public class StudentMapper {

    private static StudentMapper instance;

    private StudentMapper() {
    }

    public static StudentMapper getInstance() {
        if(StudentMapper.instance == null) {
            StudentMapper.instance = new StudentMapper();
        }

        return StudentMapper.instance;
    }

    public Student dtoToModel(StudentDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Student(dto.name(), dto.lastname());
    }
}