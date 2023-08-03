package com.java.normalsecurity.service;

import com.java.normalsecurity.model.Student;
import com.java.normalsecurity.repository.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepo studentRepo;


    public Object save(Student student){

        return studentRepo.save(student);
    }
}
