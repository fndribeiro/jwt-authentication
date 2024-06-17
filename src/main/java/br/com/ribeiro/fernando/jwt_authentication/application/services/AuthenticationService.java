package br.com.ribeiro.fernando.jwt_authentication.application.services;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ribeiro.fernando.jwt_authentication.domain.entities.LoginRequest;
import br.com.ribeiro.fernando.jwt_authentication.domain.entities.User;
import br.com.ribeiro.fernando.jwt_authentication.domain.entities.UserRequest;
import br.com.ribeiro.fernando.jwt_authentication.domain.exceptions.UnauthorizedException;
import br.com.ribeiro.fernando.jwt_authentication.ports.application.ApplicationProperties;
import br.com.ribeiro.fernando.jwt_authentication.ports.repositories.UserRepository;
import io.micrometer.common.util.StringUtils;

@Service
public class AuthenticationService {
	
	private final PasswordEncoder passwordEncoder;
	private final ApplicationProperties applicationProperties;
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationService(PasswordEncoder passwordEncoder, ApplicationProperties applicationProperties,
			UserRepository userRepository, AuthenticationManager authenticationManager) {
		this.passwordEncoder = passwordEncoder;
		this.applicationProperties = applicationProperties;
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
	}

	public void register(UserRequest userRequest) {
		
		List<String> allowedUsers = applicationProperties.allowedUsers();
		
		if (!allowedUsers.contains(userRequest.getEmail())) {
			throw new UnauthorizedException("Email " + userRequest.getEmail() + " not allowed for registration. Contact OOBT team.");
		}
		
		boolean userAlreadyExists = userRepository
			.findByEmail(userRequest.getEmail())
			.isPresent();
		
		if (userAlreadyExists) {
			throw new IllegalArgumentException("Email " + userRequest.getEmail() + " already exists.");
		}
		
		if (StringUtils.isBlank(userRequest.getName())) {
			throw new IllegalArgumentException("Property -name- cannot be null or empty.");
		}
		
		Pattern passwordPattern = Pattern.compile(applicationProperties.passwordPattern());
		
		boolean passwordMatches = passwordPattern
			.matcher(userRequest.getPassword())
			.matches();
		
		if (!passwordMatches) {
			throw new IllegalArgumentException(applicationProperties.passwordCriteria());
		}
		
		String encodedPassword = passwordEncoder.encode(userRequest.getPassword());
		
		var user = new User(
				userRequest.getName(), 
				userRequest.getEmail(), 
				encodedPassword);
		
		userRepository.save(user);
		
	}
	
	public User authenticate(LoginRequest loginRequest) {
		
		var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				loginRequest.getEmail(),
				loginRequest.getPassword()
        );
		
		return (User) authenticationManager
				.authenticate(usernamePasswordAuthenticationToken)
				.getPrincipal();
		
	}
	
}
