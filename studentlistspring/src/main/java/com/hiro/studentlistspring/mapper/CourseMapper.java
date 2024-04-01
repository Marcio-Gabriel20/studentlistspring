package com.hiro.studentlistspring.mapper;

import com.hiro.studentlistspring.dto.CourseDTO;
import com.hiro.studentlistspring.model.Course;

public class CourseMapper {
    private static CourseMapper instance;

    private CourseMapper() {

    }

    public static CourseMapper getInstance() {
        if (CourseMapper.instance == null) {
            CourseMapper.instance = new CourseMapper();
        }

        return CourseMapper.instance;
    }

    public Course dtoToModel(CourseDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Course(dto.name().trim().toUpperCase(), dto.description());
    }
}