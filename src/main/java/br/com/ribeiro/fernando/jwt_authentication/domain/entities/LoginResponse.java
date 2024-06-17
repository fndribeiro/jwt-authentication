package br.com.ribeiro.fernando.jwt_authentication.domain.entities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public record LoginResponse(String token, @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime expiresAt) {
	
	public LoginResponse(String token, Date expiresAt) {
		this(token, LocalDateTime.ofInstant(expiresAt.toInstant(), ZoneId.systemDefault()));
	}

}
