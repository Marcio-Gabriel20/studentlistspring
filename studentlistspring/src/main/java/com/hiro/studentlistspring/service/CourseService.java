package com.hiro.studentlistspring.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hiro.studentlistspring.dto.AddStudentToCourseDTO;
import com.hiro.studentlistspring.dto.CourseDTO;
import com.hiro.studentlistspring.mapper.CourseMapper;
import com.hiro.studentlistspring.model.Course;
import com.hiro.studentlistspring.model.Student;
import com.hiro.studentlistspring.repository.CourseRepository;
import com.hiro.studentlistspring.repository.StudentRepository;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @SuppressWarnings("null")
    public ResponseEntity<Void> createCourse(Course course) {
        try {
            if (isValidCourse(course)) {
                this.courseRepository.save(course);

                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
            }
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public List<Course> findAll() {
        try {
            List<Course> courses = this.courseRepository.findAll();

            if (courses != null) {
                return courses;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResponseEntity<Void> updateCourse(CourseDTO courseDto, UUID id) {
        if (id != null && courseDto != null) {
            try {
                Optional<Course> course = this.courseRepository.findById(id);

                if (course.isPresent()) {
                    Course courseUpdate = CourseMapper.getInstance().dtoToModel(courseDto);
                    courseUpdate.setId(course.get().getId());
                    courseUpdate.setStudents(course.get().getStudents());

                    this.courseRepository.save(courseUpdate);

                    return ResponseEntity.status(HttpStatus.OK).build();
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            } catch (Exception e) {
                e.printStackTrace();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }

    @SuppressWarnings("null")
    public ResponseEntity<Void> updateCoursePatch(CourseDTO courseDto, UUID id) {
        if (id != null && courseDto != null) {
            try {
                Optional<Course> course = this.courseRepository.findById(id);

                if (course.isPresent()) {
                    if (courseDto.name() != null
                            && !courseDto.name().equals(course.get().getName())) {
                        course.get().setName(courseDto.name());
                    }
                    
                    if (courseDto.description() != null
                        && !courseDto.description().equals(course.get().getDescription())) {
                        course.get().setDescription(courseDto.description());
                    }

                    this.courseRepository.save(course.get());

                    return ResponseEntity.status(HttpStatus.OK).build();
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            } catch (Exception e) {
                e.printStackTrace();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }

    @SuppressWarnings("null")
    public ResponseEntity<Void> deleteCourse(UUID id) {
        if (id != null) {
            try {
                Optional<Course> course = courseRepository.findById(id);

                if (course.isPresent()) {
                    course.get().getStudents().clear();

                    courseRepository.delete(course.get());

                    return ResponseEntity.status(HttpStatus.OK).build();
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            } catch (Exception e) {
                e.printStackTrace();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }

    @SuppressWarnings("null")
    public ResponseEntity<Void> addStudentToCourse(AddStudentToCourseDTO addStudentToCourseDTO) {
        if (addStudentToCourseDTO != null && addStudentToCourseDTO.idStudent() != null) {
            Optional<Course> course = courseRepository.findById(addStudentToCourseDTO.idCourse());
            Student student = studentRepository.findById(addStudentToCourseDTO.idStudent()).get();

            if (course.isPresent()) {
                course.get().getStudents().add(student);

                try {
                    this.courseRepository.save(course.get());

                    return ResponseEntity.status(HttpStatus.OK).build();
                } catch (Exception e) {
                    e.printStackTrace();

                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }

    private boolean isValidCourse(Course course) {
        return course != null
                && !course.getName().isBlank()
                && courseRepository.findByName(course.getName()) == null;
    }
}