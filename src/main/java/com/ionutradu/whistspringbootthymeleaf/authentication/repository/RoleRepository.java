package com.ionutradu.whistspringbootthymeleaf.authentication.repository;

import com.ionutradu.whistspringbootthymeleaf.authentication.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {

    Role findRoleByName(String role_employee);
}
