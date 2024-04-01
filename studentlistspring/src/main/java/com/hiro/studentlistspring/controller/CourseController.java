package com.hiro.studentlistspring.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hiro.studentlistspring.dto.AddStudentToCourseDTO;
import com.hiro.studentlistspring.dto.CourseDTO;
import com.hiro.studentlistspring.mapper.CourseMapper;
import com.hiro.studentlistspring.model.Course;
import com.hiro.studentlistspring.service.CourseService;

@RestController
@RequestMapping("/course")
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<Void> createCourse(@RequestBody CourseDTO courseDTO) {
        if (courseDTO.name().trim().isBlank() || courseDTO.description().trim().isBlank()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            try {
                this.courseService.createCourse(CourseMapper.getInstance().dtoToModel(courseDTO));

                return ResponseEntity.status(HttpStatus.OK).build();
            } catch (Exception e) {
                e.printStackTrace();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAll() {
        try {
            List<Course> courses = this.courseService.findAll();

            if (courses.size() != 0) {
                return ResponseEntity.status(HttpStatus.OK).body(courses);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody CourseDTO courseDto, @RequestParam UUID id) {
        if (courseDto == null || courseDto.name().trim().isBlank() || courseDto.description().trim().isBlank() || id == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            try {
                this.courseService.updateCourse(courseDto, id);

                return ResponseEntity.status(HttpStatus.OK).build();
            } catch (Exception e) {
                e.printStackTrace();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @PatchMapping
    public ResponseEntity<Void> updatePatch(@RequestBody CourseDTO courseDto, @RequestParam UUID id) {
        if (courseDto == null && id == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            try {
                this.courseService.updateCoursePatch(courseDto, id);

                return ResponseEntity.status(HttpStatus.OK).build();
            } catch (Exception e) {
                e.printStackTrace();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam UUID id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            try {
                this.courseService.deleteCourse(id);

                return ResponseEntity.status(HttpStatus.OK).build();
            } catch (Exception e) {
                e.printStackTrace();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @PostMapping("/addStudent")
    public ResponseEntity<Void> addStudentToCourse(@RequestBody AddStudentToCourseDTO addStudentToCourseDTO) {
        if (addStudentToCourseDTO.idCourse() == null || addStudentToCourseDTO.idStudent() == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            try {
                this.courseService.addStudentToCourse(addStudentToCourseDTO);

                return ResponseEntity.status(HttpStatus.OK).build();
            } catch (Exception e) {
                e.printStackTrace();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
}