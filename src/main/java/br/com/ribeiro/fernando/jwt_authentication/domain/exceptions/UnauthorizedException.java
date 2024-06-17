package br.com.ribeiro.fernando.jwt_authentication.domain.exceptions;

public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = 364290245782848475L;
	
	public UnauthorizedException(String message) {
        super(message);
    }

}
