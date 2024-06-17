package br.com.ribeiro.fernando.jwt_authentication.ports.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.ribeiro.fernando.jwt_authentication.domain.entities.ErrorResponse;
import br.com.ribeiro.fernando.jwt_authentication.domain.exceptions.ResourceNotFoundException;
import br.com.ribeiro.fernando.jwt_authentication.domain.exceptions.UnauthorizedException;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(ErrorResponse.class);
	
	@ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleException(IllegalArgumentException exception) {
		log.error(exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage());
    }
	
	@ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleException(UnauthorizedException exception) {
		log.error(exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage());
    }
	
	@ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleException(ResourceNotFoundException exception) {
		log.error(exception.getMessage(), exception);
        return new ErrorResponse(exception.getMessage());
    }

}
