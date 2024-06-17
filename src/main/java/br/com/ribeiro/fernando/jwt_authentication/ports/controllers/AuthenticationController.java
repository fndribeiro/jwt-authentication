package br.com.ribeiro.fernando.jwt_authentication.ports.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ribeiro.fernando.jwt_authentication.application.services.AuthenticationService;
import br.com.ribeiro.fernando.jwt_authentication.application.services.TokenService;
import br.com.ribeiro.fernando.jwt_authentication.domain.entities.LoginRequest;
import br.com.ribeiro.fernando.jwt_authentication.domain.entities.LoginResponse;
import br.com.ribeiro.fernando.jwt_authentication.domain.entities.User;
import br.com.ribeiro.fernando.jwt_authentication.domain.entities.UserRequest;
import br.com.ribeiro.fernando.jwt_authentication.domain.entities.UserResponse;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	private final TokenService tokenService;
	
	public AuthenticationController(AuthenticationService authenticationService, TokenService tokenService) {
		this.authenticationService = authenticationService;
		this.tokenService = tokenService;
	}

	@PostMapping("register")
	public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest) throws URISyntaxException {
		
		authenticationService.register(userRequest);
		
		return ResponseEntity
				.created(new URI("/user-details"))
				.build();
		
	}
	
	@PostMapping("login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		
		User user = authenticationService.authenticate(loginRequest);
		
		LoginResponse loginResponse = tokenService.generate(user);
		
		return ResponseEntity.ok(loginResponse);
		
	}

}
