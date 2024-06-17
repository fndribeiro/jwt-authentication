package br.com.ribeiro.fernando.jwt_authentication.application.services;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import br.com.ribeiro.fernando.jwt_authentication.domain.entities.LoginResponse;
import br.com.ribeiro.fernando.jwt_authentication.domain.entities.User;
import br.com.ribeiro.fernando.jwt_authentication.ports.application.ApplicationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {
	
	private final ApplicationProperties applicationProperties;
	
	public TokenService(ApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
	
	public LoginResponse generate(User user) {
		
		String tokenSecret = applicationProperties.tokenSecret();
		
		SecretKey secretKey = Keys.hmacShaKeyFor(tokenSecret.getBytes(StandardCharsets.UTF_8));

		var issuedAt = new Date();
		
		Date expiration = new Date(issuedAt.getTime() + applicationProperties.tokenExpiration());
		
		String token = Jwts
			.builder()
			.issuer("ribeiro.fernando")
			.subject(user.getUsername())
			.claim("email", user.getEmail())
			.claim("name", user.getName())
			.issuedAt(issuedAt)
			.expiration(expiration)
			.signWith(secretKey)
			.compact();
		
		return new LoginResponse(token, expiration);
		
	}
	
	public Claims validate(String bearerToken) {

		if (bearerToken.startsWith("Bearer ")) {
			
			String token = bearerToken.substring(7, bearerToken.length());
			
			String tokenSecret = applicationProperties.tokenSecret();
			
			SecretKey secretKey = Keys.hmacShaKeyFor(tokenSecret.getBytes(StandardCharsets.UTF_8));
			
			return Jwts
				.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
			
		}
		
		throw new IllegalArgumentException("Provide Bearer token.");

	}

}
