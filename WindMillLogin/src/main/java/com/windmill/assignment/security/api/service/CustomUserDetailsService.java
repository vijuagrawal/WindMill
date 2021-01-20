package com.windmill.assignment.security.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.windmill.assignment.security.api.model.User;
import com.windmill.assignment.security.api.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername");
		User user = repository.findByUsername(username);
		CustomUserDetails userDetails = null;
		if (user != null) {
			userDetails = new CustomUserDetails();
			userDetails.setUser(user);
		} else {
			throw new UsernameNotFoundException("User not exist with name : " + username);
		}
		return userDetails;

	}

	public User verifyAndAddUser(User user, String authProvider) {

		try {
			System.out.println("verifyAndAddUser " + user.getUsername());
			CustomUserDetails userDetail = (CustomUserDetails) loadUserByUsername(user.getUsername());
			System.out.println("User Present");
			user = userDetail.getUser();
		} catch (Exception ex) {

			System.out.println("User Not Present");
			if ("self".equals(authProvider)) {
				String pwd = user.getPassword();
				String encryptPwd = passwordEncoder.encode(pwd);
				System.out.println(encryptPwd);
				user.setPassword(encryptPwd);
			}
			user.setAuthProvider(authProvider);
			repository.save(user);
		}
		return user;
		
		

	}
	
	public List<User> getAllUser() {

		return repository.findAll();
		

	}
}
