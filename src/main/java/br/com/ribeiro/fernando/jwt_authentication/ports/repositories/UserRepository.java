package br.com.ribeiro.fernando.jwt_authentication.ports.repositories;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.ribeiro.fernando.jwt_authentication.domain.entities.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {

	Optional<User> findByEmail(String email);
	
}
