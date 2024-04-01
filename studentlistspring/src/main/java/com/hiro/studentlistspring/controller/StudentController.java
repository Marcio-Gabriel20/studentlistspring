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

import com.hiro.studentlistspring.dto.StudentDTO;
import com.hiro.studentlistspring.model.Student;
import com.hiro.studentlistspring.service.StudentService;

@RestController
@RequestMapping("/student")
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<Void> createStudent(@RequestBody StudentDTO studentDTO) {
        if (studentDTO.name().trim().isBlank() || studentDTO.lastname().trim().isBlank()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            try {
                this.studentService.createStudent(studentDTO);

                return ResponseEntity.status(HttpStatus.OK).build();
            } catch (Exception e) {
                e.printStackTrace();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAll() {

        try {
            List<Student> students = this.studentService.findAll();

            if (students.size() != 0) {
                return ResponseEntity.status(HttpStatus.OK).body(students);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody StudentDTO studentDTO, @RequestParam UUID id) {
        if (studentDTO == null || studentDTO.name().trim().isBlank() || studentDTO.lastname().trim().isBlank() || id == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            try {
                this.studentService.updateStudent(studentDTO, id);

                return ResponseEntity.status(HttpStatus.OK).build();
            } catch (Exception e) {
                e.printStackTrace();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @PatchMapping
    public ResponseEntity<Void> updatePatch(@RequestBody StudentDTO studentDTO, @RequestParam UUID id) {
        if (studentDTO == null && id == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            try {
                this.studentService.updateStudentPatch(studentDTO, id);

                return ResponseEntity.status(HttpStatus.OK).build();
            } catch (Exception e) {
                e.printStackTrace();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }

    @DeleteMapping
    public void deleteStudent(@RequestParam UUID id) {
        try {
            this.studentService.deleteStudent(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}