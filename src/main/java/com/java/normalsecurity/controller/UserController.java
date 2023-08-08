package com.java.normalsecurity.controller;

import com.java.normalsecurity.exception.UserException;
import com.java.normalsecurity.model.Student;
import com.java.normalsecurity.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/user")
public class UserController {
    private final StudentService studentService;

   private final PasswordEncoder passwordEncoder;
    @PostMapping(path = "/save")
    public ResponseEntity<Object> save(@RequestBody Student student){
        try {
           student.setPassword(passwordEncoder.encode(student.getPassword()));
            return new ResponseEntity<>(studentService.save(student), HttpStatus.OK);
        }catch(Exception e){
            throw new UserException("student not null");
        }
    }

//     @PreAuthorize("hasRole('ADMIN')")
     @PreAuthorize("hasAuthority('ADMIN')")
//    @PreAuthorize("isAuthenticated() || hasRole('ROLE_ADMIN')")
    @GetMapping(path = "/hello")
    public ResponseEntity<Object> msg(){
       try {
           return ResponseEntity.ok("this is admin role");
       }catch (Exception e){
           throw new UserException(e.getLocalizedMessage());
       }
    }

//     @PreAuthorize("hasRole('USER')")
     @PreAuthorize("hasAuthority('USER')")
//    @PreAuthorize("isAuthenticated() || hasRole('ROLE_USER')")
    @GetMapping(path = "/msg")
    public ResponseEntity<Object> msg1(){
         try {
             return ResponseEntity.ok("this is user role");
         }catch (Exception e){
             throw new UserException(e.getLocalizedMessage());
         }
    }
}
