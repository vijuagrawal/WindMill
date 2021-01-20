package com.windmill.assignment.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.windmill.assignment.security.oauth.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("timepass");
		auth.userDetailsService(userDetailsService).passwordEncoder(encodePWD());
	}
	 @Bean
	    SimpleUrlAuthenticationSuccessHandler successHandler() {
	        return new SimpleUrlAuthenticationSuccessHandler("/oauth2/success");
	    }
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().  
	      antMatchers("/user","/oauth2","signupfrom","/").permitAll()  
	      .antMatchers("/admin").authenticated()  
	      .and()  
	      .formLogin()  
	      	.loginPage("/login")
	      .and()  
	      .oauth2Login()
	          .loginPage("/login")
	          .userInfoEndpoint().userService(customOAuth2UserService)
	          .and()
	          .successHandler(successHandler())
	          
	       .and()   
	      .logout()  
	      .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		/*
		 * http .httpBasic() .and() .authorizeRequests()
		 * .antMatchers("/rest/**").permitAll() .and() .authorizeRequests()
		 * .antMatchers("/h2-console/**").permitAll() .and() .authorizeRequests()
		 * .antMatchers("/login*").permitAll() .and() .authorizeRequests()
		 * .antMatchers("/secure/**").hasAnyRole("ADMIN") .anyRequest().authenticated()
		 * .and() .formLogin().loginPage("/login.jsp")
		 * .failureUrl("/login.jsp?error=1").loginProcessingUrl("/login")
		 * .permitAll().and().logout() .logoutSuccessUrl("/listEmployees.html");
		 * .failureUrl("/login.jsp?error=1").loginProcessingUrl("/login")
		 * .permitAll().and().logout() .logoutSuccessUrl("/listEmployees.html");
		 * 
		 */

		http.headers().frameOptions().disable();
	}

	@Bean
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
}
