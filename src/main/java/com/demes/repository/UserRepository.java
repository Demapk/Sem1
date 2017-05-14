package com.demes.repository;

import com.demes.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);

    User findByUsername(String username);

    User findByEmailOrUsername(String email, String username);

}
