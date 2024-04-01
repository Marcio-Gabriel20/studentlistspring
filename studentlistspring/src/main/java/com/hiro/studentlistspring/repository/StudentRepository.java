package com.hiro.studentlistspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hiro.studentlistspring.model.Student;
import java.util.List;
import java.util.UUID;


@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Student findByEnrollment(String enrollment);

    @Query("SELECT s FROM Student s WHERE s.name = ?1 AND s.lastname = ?2")
    List<Student> findByNameAndLastname(String name, String lastname);
}