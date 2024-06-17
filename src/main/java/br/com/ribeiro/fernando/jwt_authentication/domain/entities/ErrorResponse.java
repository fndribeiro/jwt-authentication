package br.com.ribeiro.fernando.jwt_authentication.domain.entities;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ErrorResponse {
	
	private static final Logger log = LoggerFactory.getLogger(ErrorResponse.class);
	
	private final String error;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy-hh:mm:ss")
	private final Date timestamp;
	
	public ErrorResponse(String error) {
		
		timestamp = new Date();
		
		this.error = error;
		
	}

	public String getError() {
		return error;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
	@Override
	public String toString() {
		
		var objectMapper = new ObjectMapper();
		
		try {
			
			return objectMapper.writeValueAsString(this);
			
		} catch (JsonProcessingException e) {
			
			log.error(e.getMessage(), e);
			
			return "ErrorResponse [timestamp=" + timestamp + ", error= " + error + "]";
			
		}
		
	}
	
}
