package org.example.repository;

import java.util.Optional;

import org.example.entities.refreshtoken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<refreshtoken, Integer>{

    Optional<refreshtoken> findByToken(String token);
    
}
