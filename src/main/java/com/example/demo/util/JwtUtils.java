package com.example.demo.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.demo.exception.JWTTokenMalFormedException;
import com.example.demo.exception.JwtTokenMissingException;
import com.example.demo.model.Login;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {

	private static String secret = "secret123";
	private static long expiryDuration = 20 * 60; // (5-minutes, 60-seconds)

	public String generateJwt(Login login) {

		long milliTime = System.currentTimeMillis();
		long expiryTime = milliTime + expiryDuration * 1000; // (1000-milliseconds to seconds conversion)

		Date issuedAt = new Date();
		Date expiryAt = new Date(expiryTime);
		// claims(payload)
		Claims claims = Jwts.claims().setSubject(login.getEmailId()).setIssuer(login.getId().toString()).setIssuedAt(issuedAt).setExpiration(expiryAt);
		// generate jwt using claims
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();

	}

	public boolean validateToken(String token) throws JwtTokenMissingException, JWTTokenMalFormedException {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			throw new JWTTokenMalFormedException("Invalid JWT signature");
		} catch (MalformedJwtException e) {
			throw new JWTTokenMalFormedException("Invalid JWT token");
		} catch (ExpiredJwtException e) {
			throw new JWTTokenMalFormedException("Expired JWT token");
		} catch (UnsupportedJwtException e) {
			throw new JWTTokenMalFormedException("Unsupported JWT token");
		} catch (IllegalArgumentException e) {
			throw new JwtTokenMissingException("Jwt claims is empty");
		}
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}

}
