package com.hiro.studentlistspring.dto;

import java.util.UUID;

public record AddStudentToCourseDTO(UUID idCourse, UUID idStudent) {
}