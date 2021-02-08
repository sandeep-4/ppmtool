package com.java.spring.ppmtool.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.java.spring.ppmtool.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	public String generateToken(Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		Date now = new Date(System.currentTimeMillis());
		Date expiryDate = new Date(now.getTime() + 300_000);

		String userId = Long.toString(user.getId());
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", (Long.toString(user.getId())));
		claims.put("username", user.getUsername());
		claims.put("fullName", user.getFullName());

		// this generates token whenever a correct username is logged in
		return Jwts.builder().setSubject(userId).setClaims(claims).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, "SecretKeyToGenJWTs").compact();
	}

	// Validating the token

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey("SecretKeyToGenJWTs").parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {

			System.out.println("JWT signature errors");

		} catch (MalformedJwtException e) {

			System.out.println("Jwt token exception" + e);
		} catch (ExpiredJwtException e) {
			System.out.println("expired token");
		} catch (UnsupportedJwtException e) {
			System.out.println("token not supported");
		} catch (IllegalArgumentException ex) {
			System.out.println("illegal args");
		}
		return false;
	}

	// get user id from token

	public Long getIdTokenFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey("SecretKeyToGenJWTs").parseClaimsJws(token).getBody();
		String id = (String) claims.get("id");
		return Long.parseLong(id);
	}
}
