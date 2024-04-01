package com.hiro.studentlistspring.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hiro.studentlistspring.dto.StudentDTO;
import com.hiro.studentlistspring.mapper.StudentMapper;
import com.hiro.studentlistspring.model.Course;
import com.hiro.studentlistspring.model.Student;
import com.hiro.studentlistspring.repository.CourseRepository;
import com.hiro.studentlistspring.repository.StudentRepository;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public ResponseEntity<Void> createStudent(StudentDTO studentDto) {
        try {

            Student student = StudentMapper.getInstance().dtoToModel(studentDto);

            if (isValidStudent(student)) {
                if (student.getEnrollment() == null || student.getEnrollment().isBlank()) {
                    student.setEnrollment(generateEnrollment(student.getName()));
                }

                this.studentRepository.save(student);

                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
            }

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public List<Student> findAll() {
        try {
            List<Student> students = this.studentRepository.findAll();

            if (students != null) {
                return students;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public ResponseEntity<Void> updateStudent(StudentDTO studentDto, UUID id) {
        if (id != null && studentDto != null) {
            try {
                Optional<Student> student = this.studentRepository.findById(id);

                if (student.isPresent()) {
                    Student studentUpdate = StudentMapper.getInstance().dtoToModel(studentDto);
                    studentUpdate.setId(student.get().getId());
                    studentUpdate.setEnrollment(student.get().getEnrollment());

                    this.studentRepository.save(studentUpdate);

                    return ResponseEntity.status(HttpStatus.OK).build();
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            } catch (Exception e) {
                e.printStackTrace();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        return null;
    }

    @SuppressWarnings("null")
    public ResponseEntity<Void> updateStudentPatch(StudentDTO studentDto, UUID id) {
        if (id != null && studentDto != null) {
            try {
                Optional<Student> student = this.studentRepository.findById(id);

                if (student.isPresent()) {
                    Map<String, Object> updatedFields = new HashMap<>();
                    updatedFields.put("name", studentDto.name());
                    updatedFields.put("lastname", studentDto.lastname());
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }

                this.studentRepository.save(student.get());

                return ResponseEntity.status(HttpStatus.OK).build();
            } catch (Exception e) {
                e.printStackTrace();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }

    @SuppressWarnings("null")
    public ResponseEntity<Void> deleteStudent(UUID id) {
        try {
            if (id != null) {
                List<Course> courses = this.courseRepository.findAll();
                Optional<Student> student = this.studentRepository.findById(id);

                for (Course course : courses) {
                    if (course.getStudents().contains(student.get())) {
                        course.getStudents().remove(student.get());

                        this.courseRepository.save(course);
                        this.studentRepository.deleteById(student.get().getId());
                    }
                }

                return ResponseEntity.status(HttpStatus.OK).build();
            }
            
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private boolean isValidStudent(Student student) {
        return student != null
                && !student.getName().isBlank()
                && !student.getLastname().isBlank()
                && studentRepository.findByNameAndLastname(student.getName(), student.getLastname()).isEmpty();
    }

    private String generateEnrollment(String name) {
        Random random = new Random();

        int numberEnrollment = 10000000 + random.nextInt(90000000);

        String enrollment = String.valueOf(numberEnrollment) + name.trim().toUpperCase().replaceAll("\\s", "");

        return enrollment;
    }
}