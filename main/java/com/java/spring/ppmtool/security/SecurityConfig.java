package com.java.spring.ppmtool.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.java.spring.ppmtool.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	

	@Bean
	JwtAuthencationFilter jwtAuthFilter() {
		return new JwtAuthencationFilter();
		}

	
	@Autowired
	JwtAunthenciationEntryPoint unauthorizedError;
	
	@Autowired
	CustomUserDetailsService customUserDetails;
	
	@Autowired
	BCryptPasswordEncoder bcrypt;
	

	@Override
	protected void configure(AuthenticationManagerBuilder authManagerbuilder) throws Exception {
		authManagerbuilder.userDetailsService(customUserDetails).passwordEncoder(bcrypt);
		
	}



	@Override
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
		.exceptionHandling().authenticationEntryPoint(unauthorizedError).and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.headers().frameOptions().sameOrigin()
		.and()
		.authorizeRequests()
		.antMatchers("/",
				"/favicon.ico",
				"/**/*.png",
				"/**/*.gif",
				"/**/*.svg",
				"/**/*.jpg",
				"/**/*.html",
				"/**/*.css",
				"/**/*.js").permitAll()
		.antMatchers("/api/users/**").permitAll()
		.antMatchers("h2-console/**").permitAll()
		.antMatchers("/api/porject/all").permitAll()
		.anyRequest().authenticated();
	
	
	http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
