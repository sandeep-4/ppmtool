package com.java.spring.ppmtool.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.java.spring.ppmtool.model.User;
import com.java.spring.ppmtool.services.CustomUserDetailsService;

public class JwtAuthencationFilter extends OncePerRequestFilter{
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	CustomUserDetailsService customerDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String jwt=getJWTFromRequest(request);
			
			if(StringUtils.hasText(jwt)&& jwtTokenProvider.validateToken(jwt)) {
				Long userId=jwtTokenProvider.getIdTokenFromJWT(jwt);
				User userDetails=customerDetailsService.loadUserById(userId);
				
				UsernamePasswordAuthenticationToken authencaition=new UsernamePasswordAuthenticationToken(
						userDetails,null,Collections.emptyList());
				
				authencaition.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authencaition);
			}
			
		} catch (Exception e) {

		logger.error("unable to secure it !!!",e);
		}
		filterChain.doFilter(request, response);
	}

	private String getJWTFromRequest(HttpServletRequest request) {
		String bearerToken=request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		} 
		return null;
	}
}
