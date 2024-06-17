package br.com.ribeiro.fernando.jwt_authentication.domain.entities;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document
public class User implements UserDetails {
	
	private static final long serialVersionUID = 718363168065643194L;
	
	@Id
	private ObjectId id;
	private String name;
	private String email;
	private String password;
	private LocalDateTime createdAt;
	private LocalDateTime passwordExpirationDate;
	
	public User() {}

	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.createdAt = LocalDateTime.now();
		this.passwordExpirationDate = createdAt.plusMonths(3);
	}

	public ObjectId getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	public LocalDateTime getPasswordExpirationDate() {
		return passwordExpirationDate;
	}
	
	public boolean isPasswordExpired() {
		return passwordExpirationDate.isBefore(LocalDateTime.now());
	}
	
	public UserResponse mapToUserResponse() {
		return new UserResponse(this);
	}
	
}
