package br.com.ribeiro.fernando.jwt_authentication.domain.entities;

import java.time.LocalDateTime;

public record UserResponse(String name, String email, LocalDateTime createdAt, LocalDateTime passwordExpirationDate, boolean isPasswordExpired) {
	
	public UserResponse(User user) {
		this(user.getName(), user.getEmail(), user.getCreatedAt(), user.getPasswordExpirationDate(), user.isPasswordExpired());
	}
	
}
