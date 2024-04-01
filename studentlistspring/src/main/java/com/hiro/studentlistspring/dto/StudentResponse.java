package com.hiro.studentlistspring.dto;

import java.util.List;
import java.util.UUID;

public class StudentResponse {
    
    private UUID id;
    private String name;
    private String lastname;
    private String enrollment;
    private List<CourseResponse> courses;

    public StudentResponse(UUID id, String name, String lastname, String enrollment, List<CourseResponse> courses) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.enrollment = enrollment;
        this.courses = courses;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment;
    }

    public List<CourseResponse> getCourses() {
        return courses;
    }

    public void setCoursesResponse(List<CourseResponse> courses) {
        this.courses = courses;
    }
    
}