package com.java.normalsecurity.security;

import com.java.normalsecurity.model.Role;
import com.java.normalsecurity.model.Student;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class MyUserDetails implements UserDetails {

    private Student student;

    private String name;
    private String password;
    private List<GrantedAuthority> authorities;

//    public MyUserDetails(Student student) {
//        this.student = student;
//    }

    public MyUserDetails(Student student) {
        name = student.getName();
        password = student.getPassword();
        authorities = Arrays.stream(student.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .peek(data-> System.out.println("this is role: "+data))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//	private User user;
//	public MyUserDetails(User user) {
//		this.user=user;
//	}
//	
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		Set<Role> roles=user.getRoles();
//		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//		
//		for(Role role:roles) {
//			authorities.add(new SimpleGrantedAuthority(role.getName()));
//		}
//		
//		return authorities;
//	}
//
//	@Override
//	public String getPassword() {
//		// TODO Auto-generated method stub
//		return user.getPassword();
//	}
//
//	@Override
//	public String getUsername() {
//		// TODO Auto-generated method stub
//		return user.getUsername();
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		// TODO Auto-generated method stub
//		return user.isEnabled();
//	}
}
