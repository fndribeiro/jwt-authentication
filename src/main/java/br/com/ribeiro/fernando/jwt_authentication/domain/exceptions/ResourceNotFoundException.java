package br.com.ribeiro.fernando.jwt_authentication.domain.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -8334886479654682274L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
	
}
