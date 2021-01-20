package com.windmill.assignment.security.api.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.windmill.assignment.security.api.model.User;
import com.windmill.assignment.security.api.repository.UserRepository;
import com.windmill.assignment.security.api.service.CustomUserDetailsService;

@RestController
public class ApplicationController {

	@GetMapping("/process")
	public String process() {
		return "processing..";
	}

	@Autowired
	private UserRepository userRepository;

	@Autowired
	CustomUserDetailsService customUserDetailsService;
	

	@GetMapping("/admin")
	public String admin() {

		return "admin.jsp";
	}

	@RequestMapping(value = "/oauth2/success")
	public ModelAndView user(Principal principal) {

		DefaultOidcUser customOAuth2User = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String email = customOAuth2User.getEmail();

		System.out.println(email);
		System.out.println(customOAuth2User.getGivenName());
		System.out.println(customOAuth2User.getFamilyName());

		User user = new User();
		user.setUsername(email);
		user.setFirstName(customOAuth2User.getGivenName());
		user.setLastName(customOAuth2User.getFamilyName());

		user = customUserDetailsService.verifyAndAddUser(user, "Google");

		return getWelcomePage(user);
	}

	

	@PostMapping("/user/registration/add")
	public ModelAndView addUser(@ModelAttribute User user) {
		System.out.println("adding");

		System.out.println(user.getUsername());
		System.out.println(user.getFirstName());
		user = customUserDetailsService.verifyAndAddUser(user, "self");
		return getWelcomePage(user);

	}

	@GetMapping("/user/registration")
	public ModelAndView showRegistrationForm() {
		System.out.println("registration");
		ModelAndView mv = new ModelAndView("signupfrom");

		return mv;

	}
	
	@GetMapping("/user/all")
	public ResponseEntity<List<User>> showall() {
		System.out.println("getAllUser");
		return new ResponseEntity<List<User>>( customUserDetailsService.getAllUser(), HttpStatus.OK);

	}

	public ModelAndView getWelcomePage(User user) {
		System.out.println("welcome");
		ModelAndView mv = new ModelAndView("admin");
		mv.addObject(user);

		return mv;

	}
}
