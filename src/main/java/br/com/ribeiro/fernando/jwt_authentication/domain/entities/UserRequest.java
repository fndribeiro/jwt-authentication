package br.com.ribeiro.fernando.jwt_authentication.domain.entities;

public class UserRequest {
	
	private String email;
	private String password;
	private String name;
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getName() {
		return name;
	}
	
}
