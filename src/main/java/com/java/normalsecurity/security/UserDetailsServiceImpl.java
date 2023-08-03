package com.java.normalsecurity.security;

import com.java.normalsecurity.model.Student;
import com.java.normalsecurity.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.java.normalsecurity.repository.StudentRepo;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private StudentRepo studentRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Student userInfo = studentRepo.getUserByUsername(username);
//		if(userInfo.equals(null)){
//			throw new UsernameNotFoundException("User not found");
//		}
//		return new MyUserDetails(userInfo);

        Optional<Student> userInfo = studentRepo.findByName(username);
        return userInfo.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not fount " + username));

    }   

}
