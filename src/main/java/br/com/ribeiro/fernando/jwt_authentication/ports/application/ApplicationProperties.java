package br.com.ribeiro.fernando.jwt_authentication.ports.application;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public record ApplicationProperties(String tokenSecret, Long tokenExpiration, String passwordPattern, List<String> allowedUsers) {
	
	public String passwordCriteria() {
		return "Password must meet the criteria: " +
	            "at least 10 characters long, at least one number, " +
	            "at least one lowercase letter, at least one uppercase letter, " +
	            "and at least one special character.";
	}

}
