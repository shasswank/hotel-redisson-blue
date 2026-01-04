package com.example.hotelbackend.repository;



import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.hotelbackend.model.User;



public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email);

    void deleteByEmail(String email);

   Optional<User> findByEmail(String email);
}
