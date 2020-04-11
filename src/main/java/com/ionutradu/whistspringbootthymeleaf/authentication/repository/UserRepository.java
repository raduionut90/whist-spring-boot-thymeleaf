package com.ionutradu.whistspringbootthymeleaf.authentication.repository;

import com.ionutradu.whistspringbootthymeleaf.authentication.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
