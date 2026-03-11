package org.example.repository;

import org.springframework.stereotype.Repository;
import org.example.entities.userinfo;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface UserRepository extends CrudRepository<userinfo, String> {
    
    public userinfo findByUsername(String username);
}
