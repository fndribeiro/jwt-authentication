package br.com.ribeiro.fernando.jwt_authentication.application.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.ribeiro.fernando.jwt_authentication.domain.entities.LoggedUser;
import br.com.ribeiro.fernando.jwt_authentication.domain.entities.UserResponse;
import br.com.ribeiro.fernando.jwt_authentication.ports.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserResponse loadUserByLoggedUser() {
		
		var loggedUser = (LoggedUser) SecurityContextHolder
			.getContext()
			.getAuthentication()
			.getPrincipal();
		
		return userRepository
			.findByEmail(loggedUser.email())
			.map(UserResponse::new)
			.orElseThrow(() -> new UsernameNotFoundException("User " + loggedUser.email() + "  not found."));
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository
				.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found."));
	}

}
