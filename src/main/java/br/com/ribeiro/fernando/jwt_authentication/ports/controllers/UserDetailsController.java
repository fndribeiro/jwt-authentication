package br.com.ribeiro.fernando.jwt_authentication.ports.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ribeiro.fernando.jwt_authentication.application.services.UserDetailsServiceImpl;
import br.com.ribeiro.fernando.jwt_authentication.domain.entities.UserResponse;

@RestController
@RequestMapping("user-details")
public class UserDetailsController {
	
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	
	public UserDetailsController(UserDetailsServiceImpl userDetailsServiceImpl) {
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}
	
	@GetMapping
	public ResponseEntity<UserResponse> userDetails() {
		
		UserResponse userResponse = userDetailsServiceImpl.loadUserByLoggedUser();
		
		return ResponseEntity.ok(userResponse);
	}

}
