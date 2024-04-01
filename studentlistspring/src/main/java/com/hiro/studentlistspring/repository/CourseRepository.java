package com.hiro.studentlistspring.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hiro.studentlistspring.model.Course;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    Course findByName(String name);
}