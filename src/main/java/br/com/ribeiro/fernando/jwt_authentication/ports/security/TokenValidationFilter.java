package br.com.ribeiro.fernando.jwt_authentication.ports.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.ribeiro.fernando.jwt_authentication.application.services.TokenService;
import br.com.ribeiro.fernando.jwt_authentication.domain.entities.ErrorResponse;
import br.com.ribeiro.fernando.jwt_authentication.domain.entities.LoggedUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenValidationFilter extends OncePerRequestFilter {
	
	private static final Logger log = LoggerFactory.getLogger(TokenValidationFilter.class);
	
	private final TokenService tokenService;
	
	public TokenValidationFilter(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if (token != null) {
			
			try {
				
				Claims claims = tokenService.validate(token);
				
				var email = (String) claims.get("email");
				
				var name = (String) claims.get("name");
				
				var loggedUser = new LoggedUser(email, name);
				
				var authenticationToken = new UsernamePasswordAuthenticationToken(loggedUser, null, null);
				
				WebAuthenticationDetails webAuthenticationDetails = new WebAuthenticationDetailsSource()
					.buildDetails(request);
				
				authenticationToken.setDetails(webAuthenticationDetails);
				
				SecurityContextHolder
					.getContext()
					.setAuthentication(authenticationToken);
				
			} catch (ExpiredJwtException e) {
				
				log.error(e.getMessage(), e);
				
				var errorResponse = new ErrorResponse("Expired token.");
				
				response
					.getOutputStream()
					.print(errorResponse.toString());
				
			} catch (MalformedJwtException e) {
				
				log.error(e.getMessage(), e);
				
				var errorResponse = new ErrorResponse("Malformed token.");
				
				response
					.getOutputStream()
					.print(errorResponse.toString());
				
			} catch (Exception e) {
				
				log.error(e.getMessage(), e);
				
				var errorResponse = new ErrorResponse(e.getMessage());
				
				response
					.getOutputStream()
					.print(errorResponse.toString());
				
			}
			
		}
		
		filterChain.doFilter(request, response);
		
	}
	
}
