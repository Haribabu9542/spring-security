package com.java.normalsecurity.repository;

import com.java.normalsecurity.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Integer> {

    // @Query("SELECT u from User u Where u.name = :name")
    // public Student getUserByUsername(@Param("name") String name);
    // public Student getUserByUsername(String name);
    // Optional<Student> findByName(String username);

   
}
